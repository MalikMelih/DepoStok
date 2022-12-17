package com.example.depostok.obj;

public class DataHome {
    int mImageResource;
    String mActivity;
    String mlabel;
    String mdesc1;
    String mdesc2;

    public DataHome(String activity, String text1, String text2, String text3, int imageResource) {
        mImageResource = imageResource;
        mActivity = activity;
        mlabel = text1;
        mdesc1 = text2;
        mdesc2 = text3;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getActivity() {
        return mActivity;
    }

    public String getLabel() {
        return mlabel;
    }

    public String getDesc1() {
        return mdesc1;
    }

    public String getDesc2() {
        return mdesc2;
    }
}
