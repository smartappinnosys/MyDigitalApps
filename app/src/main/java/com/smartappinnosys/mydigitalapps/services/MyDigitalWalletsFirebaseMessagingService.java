package com.smartappinnosys.mydigitalapps.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartappinnosys.mydigitalapps.MyDigitalMainActivity;
import com.smartappinnosys.mydigitalapps.util.NotificationUtils;

/**
 * Created by SmartApp Innosys on 3/1/2017.
 */
public class MyDigitalWalletsFirebaseMessagingService extends FirebaseMessagingService {



    private static final String TAG = "MyAndroidFCMService";

    public MyDigitalWalletsFirebaseMessagingService(){}

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
      //  Log.d(TAG, "From: " + remoteMessage.getFrom());
      //  Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //create notification
        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            createNotification(remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String message = remoteMessage.getData().get("body");
            String title = remoteMessage.getData().get("title");
          // String img = remoteMessage.getData().get("image");
       //     int imgId = Integer.parseInt(img);
            showNotificationMessage(message,title);
        }

    }

    private void createNotification(String messageBody) {


        if (!NotificationUtils.isAppInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent();
            pushNotification.setAction("android.intent.action.PUSH_NOTIFICATION");
            pushNotification.putExtra("message", messageBody);
            sendBroadcast(pushNotification);
            Log.d(TAG, "in Foreground ");



        }

    }

    private void showNotificationMessage( String message, String title) {
        NotificationUtils mNotification = new NotificationUtils(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), MyDigitalMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.d("Badri", "in Background-here shownotification ");

        mNotification.showNotificationMessage(message, intent, title);
    }


}
