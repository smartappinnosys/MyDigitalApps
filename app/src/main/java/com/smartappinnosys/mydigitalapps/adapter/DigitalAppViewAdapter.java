package com.smartappinnosys.mydigitalapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.smartappinnosys.mydigitalapps.R;
import com.smartappinnosys.mydigitalapps.model.DigitalAppVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by daimajia on 14-5-29.
 */
public class DigitalAppViewAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private ArrayList<DigitalAppVO> mDigitalAppList;
    private List<DigitalAppVO>list;
    DigitalAppVO mDigitalAppVO;
    private TypedArray navMenuIcons;
    Button mButtonIconl;
    ValueFilter valueFilter;
    int appState;
    static String appPackageName [];
    private TypedArray appPackageNameArray;
    InterstitialAd mInterstitialAd;
    public DigitalAppViewAdapter(Context context) {
        mContext = context;

      //  list=mDigitalAppList;
        navMenuIcons = mContext.getResources()
                .obtainTypedArray(R.array.app_menu_icons);
        appPackageNameArray = mContext.getResources().obtainTypedArray(R.array.app_packages_list);
        appPackageName= mContext.getResources().getStringArray(R.array.app_packages_list);
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getString(R.string.interstitial_ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
        populateDigitalApps();
        list=mDigitalAppList;

    }


    @Override
    public int getCount() {
        return mDigitalAppList.size();
    }

    @Override
    public DigitalAppVO getItem(int position) {
        return (DigitalAppVO)this.mDigitalAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDigitalAppList.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.app_view_display, null);
            holder = new ViewHolder();
            holder.localTextView1 = (TextView) convertView.findViewById(R.id.app_name);
            holder.localTextView2 = (TextView) convertView.findViewById(R.id.app_desc);
            holder.mImageAppIcon = (ImageView)convertView.findViewById(R.id.counter_img);

            convertView.setTag(holder);

        }



        mDigitalAppVO = getItem(position);
        int size= getCount();
        holder = (ViewHolder) convertView.getTag();

        //   for(int i =0; i< size; i++)

          //   mImageAppIcon.setImageResource(navMenuIcons.getResourceId(i, -1));
        holder.localTextView1.setText(mDigitalAppVO.getmDigitalAppName());
        holder.localTextView2.setText(mDigitalAppVO.getmDigitalAppDesc());
        mButtonIconl      = (Button)convertView.findViewById(R.id.download_img);

        final boolean isAppInstalled = appInstalledOrNot(appPackageName[position]);
        if(isAppInstalled)
            mButtonIconl.setText("OPEN");
        else
           mButtonIconl.setText("INSTALL");

       holder.mImageAppIcon.setImageResource(navMenuIcons.getResourceId(position, -1));
     //   holder.mImageAppIcon.setImageResource(mDigitalAppVO.getIconID());


        mButtonIconl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAppInstalled) {
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            showInterstitial();
                        }
                    });
                    try {

                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName[position])));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName[position])));
                    }

                } else {

                        Intent LaunchIntent = mContext.getPackageManager()
                                .getLaunchIntentForPackage(appPackageName[position]);
                        if (LaunchIntent!=null)
                            view.getContext().startActivity(LaunchIntent);
                        else
                           Toast.makeText(mContext.getApplicationContext(), "Please Enable this App to Open as It is in Disable State", Toast.LENGTH_SHORT).show();

                }
                notifyDataSetChanged();
            }
        });


        return convertView;
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = mContext.getPackageManager();

        try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                return true;
        } catch (PackageManager.NameNotFoundException e)

        {
        }

        return false;
    }
  /*  private boolean isComponentEnabled() {
        try {
            PackageManager pm = mContext.getPackageManager();
            final int state = pm.getComponentEnabledSetting(mComponentName);
            return state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    && state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    && state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED;
        } catch (NullPointerException e) {
            // Seems this will thrown on older devices
            return false;
        }
    }*/


    void populateDigitalApps()
    {

        mDigitalAppList = new ArrayList<DigitalAppVO>();
        mDigitalAppVO = new DigitalAppVO("Paytm","Paytm for online recharge & mobile bills, DTH recharge & utility bills, travel, movies, shopping or to send or accept payments");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("BHIM","Bharat Interface for Money (BHIM) is an initiative to enable fast, secure, reliable cashless payments through your mobile phone");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("SBI Buddy","State Bank Of India Mobile Wallet");
       this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("MobiKwik","MobiKwik - Fastest Online Recharges, Bill Payments, Shopping & Bus Booking Made Easy");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("PhonePe","PhonePe is a one stop UPI-based app which can serve all your payment needs, in a safe and secure manner");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Freecharge"," FreeCharge makes all your Prepaid, Post-paid, DTH and Electricity bill payments through cashless transactions");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Oxygen Wallet","Oxigen Wallet is one of the most loved & widely accepted mobile wallet apps that is designed to meet all your payment needs");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("JioMoney","JioMoney Your simple, smart and secure one-app solution to everyday transactions on the go.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Pockets","Pockets by ICICI Bank - The most comprehensive mobile wallet to (1) Pay Anyone, (2) Shop Anywhere and (3) Bank with ease for any bank's customer");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("PayUmoney","PayUmoney mobile app to Recharge, Pay Bills and keep all your spending organized in one place");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Chillr","Chillr is the one stop solution for all your payment & mobile banking needs.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("PayzApp","PayZapp Recharge, Online Bill Payments, mVisa Payments, Travel, Shopping, Movies and more in just One Click. Available to customers of all banks");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Ola Money","Ola Money wallet is here with all new user experience and one tap access to all your daily needs.it can be recharged via Credit Card, Debit Card, and Net Banking");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Yes Pay Wallet","YES PAY is a Digital Wallet enabling Social Media & smart phone users to register seamlessly and then spend wisely");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("United Wallet","United Wallet : Wallet services from United Bank of India for Mobile Recharge , Bill Payment , Fund Transfer, DTH Recharge");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("PNB Kitty","PNB Kitty is secure, fast and convenient. Using PNB Kitty, all your payments are just few clicks away. The app is available to both PNB and Non-PNB customers.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("UCO Pay","UCOPAY is a semi closed digital wallet application and caters your financial needs like bill Payments including Gas, Electricity, Insurance, Mutual Funds etc.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("VPay Qwik","VPayQwik wallet offers mobile top up, recharge, bill payments such as electricity / landline / post-paid / gas, bus/air ticket, online shopping and many more.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("PayApt","IDBI Bank PayApt is a complete digital payment solution, giving you the power to pay in just One Tap.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("My Airtel-Recharge","The My Airtel App is now not just the fastest & most secure way to recharge Mobile & DTH, pay postpaid & broadband bills.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Vodafone M-Pesa","M-Pesa digital wallet, you can make safe and hassle-free transactions through your mobile and to make prepaid, post-paid, DTH and Electricity bill payments.");
        this.mDigitalAppList.add(mDigitalAppVO);
        mDigitalAppVO = new DigitalAppVO("Idea Money Wallet"," Idea Money digital wallet for  online mobile,data card recharge, DTH recharge,postpaid & utility bills, transfer money to another wallet or bank account instantly");
        this.mDigitalAppList.add(mDigitalAppVO);



    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public static class ViewHolder {


        TextView localTextView1;
        TextView localTextView2;
        ImageView mImageAppIcon,mImageDwnIcon;
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                String searchStr = constraint.toString().toLowerCase();//do this once.
                ArrayList<DigitalAppVO> filterList = new ArrayList<DigitalAppVO>();
                for (int i = 0; i < mDigitalAppList.size(); i++) {
                    if ( (mDigitalAppList.get(i).getmDigitalAppName().toLowerCase().toString() )
                            .contains(searchStr)) {

                        DigitalAppVO digitalAppp = new DigitalAppVO(mDigitalAppList.get(i)
                                .getmDigitalAppName() ,  mDigitalAppList.get(i).getmDigitalAppName());

                        filterList.add(digitalAppp);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = list.size();
                results.values = list;
            }
            return results;

        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence arg0, FilterResults results) {
            // TODO Auto-generated method stub
            mDigitalAppList = (ArrayList<DigitalAppVO>) results.values;
            notifyDataSetChanged();
        }

    }


}
