package com.example.mastif;

public class cardSong {
    private int mImageResouce;
    private String mText1;
    private String mText2;

    public cardSong(int imageResouce, String text1, String text2) {
        mImageResouce = imageResouce;
        mText1 = text1;
        mText2 = text2;
    }

    public int getImageResouce() {
        return mImageResouce;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }


}
