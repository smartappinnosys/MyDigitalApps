package com.smartappinnosys.mydigitalapps.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.smartappinnosys.mydigitalapps.R;

import java.util.List;

/**
 * Created by SmartApp Innosys on 3/1/2017.
 */
public class NotificationUtils {
    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }


    public static boolean isAppInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
     /*   if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {*/
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }
        /*else {
            String packageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }*/


        return isInBackground;
    }
    public void showNotificationMessage(String message,  Intent intent,String title) {
        //showNotificationMessage(title, message, timeStamp, intent, null);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // final int icon = R.drawable.ic_launcher_ttdinfo;
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        int smallIcon = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? R.mipmap.ic_mydigitalwallets : R.mipmap.ic_mydigitalwallets;
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.digital_wallet_slide));
        inboxStyle.addLine(message);
        Notification notification;
        notification = mBuilder.setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(notificationSoundURI)
                .setStyle(inboxStyle)
                .setStyle(style)
                .setColor(Color.RED)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_mydigitalwallets))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

    public void showDataPayloadNotificationMessage(String message,  Intent intent,String title,int image) {
        //showNotificationMessage(title, message, timeStamp, intent, null);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // final int icon = R.drawable.ic_launcher_ttdinfo;
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        int smallIcon = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? R.mipmap.ic_mydigitalwallets : R.mipmap.ic_mydigitalwallets;
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);
        Notification notification;
        notification = mBuilder.setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(notificationSoundURI)
                .setStyle(inboxStyle)
                .setColor(Color.RED)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),image))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

}
