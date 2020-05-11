package com.example.chatandroid.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatandroid.R;
import com.example.chatandroid.model.Message;
import com.example.chatandroid.util.HTTPSWebUtilDomi;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter {

    private ArrayList<Message> messages;
    private View root;
    private String userID = "";

    public MessagesAdapter(){
        messages = new ArrayList<>();
        View root = null;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());


        if(userID.equals(messages.get(position).getUserId())){
            root = inflater.inflate(R.layout.message_row_mine,null);
        }else{
            root = inflater.inflate(R.layout.message_row_others,null);
        }

        TextView messageRow = root.findViewById(R.id.message_row);
        messageRow.setText(messages.get(position).getBody());

        if(messages.get(position).getType() == Message.TYPE_IMAGE){
            ImageView imageRow = root.findViewById(R.id.imageRow);
            imageRow.setVisibility(View.VISIBLE);

            String nameImage = messages.get(position).getId();
            File imageFile = new File(viewGroup.getContext().getExternalFilesDir(null)+"/"+nameImage);

            //verify if image is already load
            if(imageFile.exists()){
                loadImage(imageRow,imageFile);
            }else{
                FirebaseStorage storage = FirebaseStorage.getInstance();
                storage.getReference().child("chats").child(nameImage).getDownloadUrl()
                        .addOnSuccessListener(
                                uri -> {
                                    File f = new File(viewGroup.getContext().getExternalFilesDir(null)+"/"+nameImage);

                                    new Thread(
                                            ()->{
                                                HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                                utilDomi.saveURLImageOnFile(uri.toString(),f);
                                                root.post(
                                                        ()->{
                                                            loadImage(imageRow,f);
                                                        }
                                                );
                                            }
                                    ).start();
                                }
                        );
            }
        }
        return root;
    }

    private void loadImage(ImageView imageView, File f) {
        Bitmap bitmap = BitmapFactory.decodeFile(f.toString());
        imageView.setImageBitmap(bitmap);
    }

    public void addMessage(Message message){
        messages.add(message);
        notifyDataSetChanged();

    }

    public void setUserID(String userID) {
        this.userID = userID;
        notifyDataSetChanged();
    }
}
