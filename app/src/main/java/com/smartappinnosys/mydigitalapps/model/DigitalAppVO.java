package com.smartappinnosys.mydigitalapps.model;

/**
 * Created by SmartApp Innosys on 2/6/2017.
 */
public class DigitalAppVO  {

    private String mDigitalAppName;
    private String mDigitalAppDesc;
    private int mIconID;
    private String mPackageName;

    public DigitalAppVO(String mDigitalAppName, String mDigitalAppDesc) {
        this.mDigitalAppName = mDigitalAppName;
        this.mDigitalAppDesc = mDigitalAppDesc;
        this.mIconID=mIconID;
        this.mPackageName=mPackageName;
    }

    public String getmDigitalAppDesc() {
        return mDigitalAppDesc;
    }

    public void setmDigitalAppDesc(String mDigitalAppDesc) {
        this.mDigitalAppDesc = mDigitalAppDesc;
    }

    public String getmDigitalAppName() {
        return mDigitalAppName;
    }

    public void setmDigitalAppName(String mDigitalAppName) {
        this.mDigitalAppName = mDigitalAppName;
    }
    public int getIconID() {
        return mIconID;
    }
    public String getmPackageName() {
        return mPackageName;
    }

}
