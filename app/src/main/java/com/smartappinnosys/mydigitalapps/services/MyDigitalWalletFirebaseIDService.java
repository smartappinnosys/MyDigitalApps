package com.smartappinnosys.mydigitalapps.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smartappinnosys.mydigitalapps.R;

/**
 * Created by SmartApp Innosys on 3/1/2017.
 */
public class MyDigitalWalletFirebaseIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIDService";

    public MyDigitalWalletFirebaseIDService(){}
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        FirebaseMessaging.getInstance().subscribeToTopic("digitalwallets");

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        FirebaseMessaging.getInstance().subscribeToTopic("digitalwallets");
    }
}
