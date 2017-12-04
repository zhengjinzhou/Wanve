package com.example.wanve4k.wanve.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2017/11/29.
 */

public class MailBean implements Parcelable {

    /**
     * KSName : 局领导
     * UserList : [{"UserName":"江善东","MobileNumber":"13602315259","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"依林","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"孔方彬","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"梁富民","MobileNumber":"","MobileNumber2":"13580753393","OfficeNumber":"22023986","HomeNumber":"","ShortNumber":"667432","Email":"","Address":""},{"UserName":"王莉莉","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"领导","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"聂江尧","MobileNumber":"13763184969","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"钟志豪","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"彭浩峻","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""},{"UserName":"郭嘉欣","MobileNumber":"","MobileNumber2":"","OfficeNumber":"","HomeNumber":"","ShortNumber":"","Email":"","Address":""}]
     */

    private String KSName;
    private List<UserListBean> UserList;

    public MailBean(Parcel in) {
        this.KSName = in.readString();
        this.UserList = new ArrayList<UserListBean>();
        in.readList(this.UserList, UserListBean.class.getClassLoader());
    }

    public static final Creator<MailBean> CREATOR = new Creator<MailBean>() {
        @Override
        public MailBean createFromParcel(Parcel in) {
            return new MailBean(in);
        }

        @Override
        public MailBean[] newArray(int size) {
            return new MailBean[size];
        }
    };

    public String getKSName() {
        return KSName;
    }

    public void setKSName(String KSName) {
        this.KSName = KSName;
    }

    public List<UserListBean> getUserList() {
        return UserList;
    }

    public void setUserList(List<UserListBean> UserList) {
        this.UserList = UserList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(KSName);
    }

    public static class UserListBean {
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

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String MobileNumber) {
            this.MobileNumber = MobileNumber;
        }

        public String getMobileNumber2() {
            return MobileNumber2;
        }

        public void setMobileNumber2(String MobileNumber2) {
            this.MobileNumber2 = MobileNumber2;
        }

        public String getOfficeNumber() {
            return OfficeNumber;
        }

        public void setOfficeNumber(String OfficeNumber) {
            this.OfficeNumber = OfficeNumber;
        }

        public String getHomeNumber() {
            return HomeNumber;
        }

        public void setHomeNumber(String HomeNumber) {
            this.HomeNumber = HomeNumber;
        }

        public String getShortNumber() {
            return ShortNumber;
        }

        public void setShortNumber(String ShortNumber) {
            this.ShortNumber = ShortNumber;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        @Override
        public String toString() {
            return "{" +
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
    }

    @Override
    public String toString() {
        return "{" +
                "KSName='" + KSName + '\'' +
                ", UserList=" + UserList +
                '}';
    }
}
