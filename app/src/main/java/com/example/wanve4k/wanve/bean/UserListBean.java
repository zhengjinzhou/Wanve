package com.example.wanve4k.wanve.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhou on 2017/11/29.
 */

public class UserListBean implements Parcelable {

    /**
     * UserName : 江善东
     * MobileNumber : 13602315259
     * MobileNumber2 :
     * OfficeNumber :
     * HomeNumber :
     * ShortNumber :
     * Email :
     * Address :
     */

    private String UserName;
    private String MobileNumber;
    private String MobileNumber2;
    private String OfficeNumber;
    private String HomeNumber;
    private String ShortNumber;
    private String Email;
    private String Address;

    protected UserListBean(Parcel in) {
        UserName = in.readString();
        MobileNumber = in.readString();
        MobileNumber2 = in.readString();
        OfficeNumber = in.readString();
        HomeNumber = in.readString();
        ShortNumber = in.readString();
        Email = in.readString();
        Address = in.readString();
    }

    public static final Creator<UserListBean> CREATOR = new Creator<UserListBean>() {
        @Override
        public UserListBean createFromParcel(Parcel in) {
            return new UserListBean(in);
        }

        @Override
        public UserListBean[] newArray(int size) {
            return new UserListBean[size];
        }
    };

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getMobileNumber2() {
        return MobileNumber2;
    }

    public void setMobileNumber2(String mobileNumber2) {
        MobileNumber2 = mobileNumber2;
    }

    public String getOfficeNumber() {
        return OfficeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        OfficeNumber = officeNumber;
    }

    public String getHomeNumber() {
        return HomeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        HomeNumber = homeNumber;
    }

    public String getShortNumber() {
        return ShortNumber;
    }

    public void setShortNumber(String shortNumber) {
        ShortNumber = shortNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "UserListBean{" +
                "UserName='" + UserName + '\'' +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", MobileNumber2='" + MobileNumber2 + '\'' +
                ", OfficeNumber='" + OfficeNumber + '\'' +
                ", HomeNumber='" + HomeNumber + '\'' +
                ", ShortNumber='" + ShortNumber + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserName);
        dest.writeString(MobileNumber);
        dest.writeString(MobileNumber2);
        dest.writeString(OfficeNumber);
        dest.writeString(HomeNumber);
        dest.writeString(ShortNumber);
        dest.writeString(Email);
        dest.writeString(Address);
    }

}
