package com.example.chatandroid.service;

import android.util.Log;

import com.example.chatandroid.model.Message;
import com.example.chatandroid.util.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

public class FCMService extends FirebaseMessagingService {

    public static final String API_KEY = "AAAAcaKHinE:APA91bE2ayyskw2bXLYSR82Ln9lKsXMkTk08UhklXctrWmNSKu1ZqnsxzY1wNFYSU4T67GSWw0l4JRiz_vHRjy3as--N8Bs3YmgBnHgVKtmYBrd3OtC7qRq56tezA70vZng-pjHCx-8C";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        JSONObject object = new JSONObject(remoteMessage.getData());
        Gson gson = new Gson();
        Message message = gson.fromJson(object.toString(),Message.class);

        NotificationUtil.createNotification(this,message.getBody());
    }
}
