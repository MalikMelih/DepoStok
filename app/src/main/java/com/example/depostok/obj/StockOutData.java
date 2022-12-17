package com.example.depostok.obj;

public class StockOutData {
        int mImageResource;
        String mActivity;
        String mlabel;
        String mdesc1;

        public StockOutData(String activity, String text1, String text2, int imageResource) {
            mImageResource = imageResource;
            mActivity = activity;
            mlabel = text1;
            mdesc1 = text2;
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

}
