package com.example.wanve4k.wanve.bean;

/**
 * Created by zhou on 2017/11/29.
 */

public class TitleBarBean {

    private String centerText;
    private String leftText;
    private String rightText;
    private boolean leftIsVisible;
    private boolean RightIsVisible;
    private int rightImageResoure;

    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public boolean isLeftIsVisible() {
        return leftIsVisible;
    }

    public void setLeftIsVisible(boolean leftIsVisible) {
        this.leftIsVisible = leftIsVisible;
    }

    public boolean isRightIsVisible() {
        return RightIsVisible;
    }

    public void setRightIsVisible(boolean rightIsVisible) {
        RightIsVisible = rightIsVisible;
    }

    public int getRightImageResoure() {
        return rightImageResoure;
    }

    public void setRightImageResoure(int rightImageResoure) {
        this.rightImageResoure = rightImageResoure;
    }

    @Override
    public String toString() {
        return "TitleBarBean{" +
                "centerText='" + centerText + '\'' +
                ", leftText='" + leftText + '\'' +
                ", rightText='" + rightText + '\'' +
                ", leftIsVisible=" + leftIsVisible +
                ", RightIsVisible=" + RightIsVisible +
                ", rightImageResoure=" + rightImageResoure +
                '}';
    }
}

