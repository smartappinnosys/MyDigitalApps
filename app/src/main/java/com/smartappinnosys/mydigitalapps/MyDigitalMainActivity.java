package com.smartappinnosys.mydigitalapps;


import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smartappinnosys.mydigitalapps.adapter.DigitalAppViewAdapter;
import com.smartappinnosys.mydigitalapps.util.NotificationUtils;

import java.util.HashMap;

public class MyDigitalMainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;
    private PagerIndicator mPageIndicator;
    private DigitalAppViewAdapter myDigitalAdapter;
    private AdView mAdView;
    ListView l;
    IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_digital_main);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mPageIndicator = (PagerIndicator) findViewById(R.id.custom_indicator);

        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.intent.action.PUSH_NOTIFICATION");
        registerReceiver(mIntentReceiver, mIntentFilter);
        FirebaseMessaging.getInstance().subscribeToTopic("digitalwallets");
      //  String msg = getString(R.string.msg_subscribed);
       // Log.d(TAG, msg);
      //  String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast

      //  Toast.makeText(MyDigitalMainActivity.this, token, Toast.LENGTH_SHORT).show();
       // Toast.makeText(MyDigitalMainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
        //mAdView.setVisibility(View.GONE);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Digital Transactions", R.mipmap.digital_wallet_guidelines);
        //  file_maps.put("Digital India", R.drawable.digitalindia_banner);
        file_maps.put("My Digital Wallet", R.mipmap.digital_wallet_slide);
        file_maps.put("Life with Digital Apps ", R.mipmap.digital_wallet_slide2);
        //   file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("hello world", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);
        l = (ListView) findViewById(R.id.transformers);
        myDigitalAdapter = new DigitalAppViewAdapter(this);
        l.setAdapter(myDigitalAdapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());.
                //    Toast.makeText(getApplicationContext(), "clicked at "+position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        super.onStop();
        mDemoSlider.stopAutoCycle();

    }

    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (mIntentReceiver != null) {
            unregisterReceiver(mIntentReceiver);
            mIntentReceiver = null;
        }


    }

    protected void onResume() {
        super.onResume();
        mDemoSlider.startAutoCycle();

        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //  Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shareto: {
                Intent sharingIntent = new Intent(
                        android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey !! Let me recommend you My Digital Wallets Android SmartApp at https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "My Digital Wallets");
                startActivity(Intent
                        .createChooser(sharingIntent, "Choose to share"));
            }
            break;
            case R.id.action_rate_app: {
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }

            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context mContext, Intent mIntent) {
            //do something
            if (mIntent.getAction().equals("android.intent.action.PUSH_NOTIFICATION")) {
                // new push notification is received
                String message = mIntent.getStringExtra("message");
                // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplication(), MyDigitalMainActivity.class);

                showNotificationMessage(getApplicationContext(), message, intent);

                Toast toast = Toast.makeText(getApplicationContext(), "Notification Alert: " + message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        }
    };

    private void showNotificationMessage(Context context, String message, Intent intent) {
        NotificationUtils mNotification = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //  Log.d("Badri", "in Background-here shownotification ");

        mNotification.showNotificationMessage(message, intent,"My Digital Test");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
