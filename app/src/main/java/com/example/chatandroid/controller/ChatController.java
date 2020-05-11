package com.example.chatandroid.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatandroid.R;
import com.example.chatandroid.model.FCMMessage;
import com.example.chatandroid.model.Message;
import com.example.chatandroid.model.User;
import com.example.chatandroid.service.FCMService;
import com.example.chatandroid.util.HTTPSWebUtilDomi;
import com.example.chatandroid.util.UtilDomi;
import com.example.chatandroid.view.ChatActivity;
import com.example.chatandroid.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class ChatController implements View.OnClickListener{

    private static final int GALLERY_CALLBACK = 1;
    private ChatActivity activity;

    private String chatRoom;
    private User user;
    private MessagesAdapter adapter;
    private Uri tempUri;


    public ChatController(final ChatActivity activity){

        this.activity = activity;

        adapter = new MessagesAdapter();
        activity.getMessagesList().setAdapter(adapter);

        chatRoom = activity.getIntent().getExtras().getString("chatRoom");

        activity.getSendBtn().setOnClickListener(this);
        activity.getGalBtn().setOnClickListener(this);
        activity.getSignOutBtn().setOnClickListener(this);



        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                adapter.setUserID(user.getId());
                activity.getUsernameTV().setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("chats").child(chatRoom).limitToLast(10);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                adapter.addMessage(message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendBtn:
                String body = activity.getMessageET().getText().toString();

                    String pushId =  FirebaseDatabase.getInstance().getReference().child("user").child(chatRoom).push().getKey();
                    Message message = new Message(
                            tempUri == null?Message.TYPE_TEXT:Message.TYPE_IMAGE,
                            pushId,
                            body,
                            user.getId(),
                            Calendar.getInstance().getTime().getTime());

                    FCMMessage fcm = new FCMMessage();
                    fcm.setTo("/topics/"+ chatRoom);
                    fcm.setData(message);
                    Gson gson = new Gson();
                    String json = gson.toJson(fcm);

                    new Thread(
                            ()->{
                                HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                utilDomi.POSTtoFCM(FCMService.API_KEY,json);
                            }
                    ).start();

                    if(tempUri!=null){
                        Log.e(">>>","temp uri");
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        storage.getReference().child("chats").child(message.getId()).putFile(tempUri)
                        .addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference().child("chats").child(chatRoom).child(pushId).setValue(message);
                                    }
                                }
                        );
                    }else{
                        FirebaseDatabase.getInstance().getReference().child("chats").child(chatRoom).child(pushId).setValue(message);
                    }
                    activity.hideImage();
                    tempUri = null;


                break;

            case R.id.galBtn:

                Intent gal = new Intent(Intent.ACTION_GET_CONTENT);
                gal.setType("image/*");
                activity.startActivityForResult(gal,GALLERY_CALLBACK);
                break;

            case R.id.signOutBtn:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(activity, MainActivity.class);
                activity.startActivity(i);
                activity.finish();
                break;
        }
    }

    public void beforePause() {
        FirebaseMessaging.getInstance().subscribeToTopic(chatRoom).addOnCompleteListener(
                task->{
                    if(task.isSuccessful()){

                    }
                }
        );
    }

    public void beforeResume() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(chatRoom);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_CALLBACK && resultCode == RESULT_OK) {
            tempUri = data.getData();
            File file = new File(UtilDomi.getPath(activity,tempUri));
            Bitmap image = BitmapFactory.decodeFile(file.toString());
            activity.getMessageIV().setImageBitmap(image);
            activity.ahowImage();
        }
    }
}
