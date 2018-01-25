package com.example.android.newsfeed;

import android.graphics.Bitmap;

public class News {

    private String mWebTitle;

    private String mSectionName;

    private String mWebPublicationDate;

    private Bitmap mThumbnail = null;

    private String mUrl;

    public News(String webTitle, String sectionName, String webPublicationDate, Bitmap thumbnail, String url) {
        mWebTitle = webTitle;
        mSectionName = sectionName;
        mWebPublicationDate = webPublicationDate;
        mThumbnail = thumbnail;
        mUrl = url;
    }

    public News(String webTitle, String sectionName, String webPublicationDate, String url) {
        mWebTitle = webTitle;
        mSectionName = sectionName;
        mWebPublicationDate = webPublicationDate;
        mUrl = url;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean hasThumbnail() {
        return mThumbnail != null;
    }
}
