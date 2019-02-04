package in.avilaksh.vedioplayer;

import android.os.Bundle;

import java.util.ArrayList;

public class VedioFileModel {
    private String mTitle;
    private String mSubTitle;
    private String mUrl_FilePath;
    private String mContentType;
    private int mDuration;
    private String mDisplayName;

    public String getmDisplayName() {
        return mDisplayName;
    }

    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public VedioFileModel() {

    }

    public VedioFileModel(String mTitle, String mSubTitle, String mUrl_FilePath, String mContentType, int mDuration, String mDisplayName) {
        this.mTitle = mTitle;
        this.mSubTitle = mSubTitle;
        this.mUrl_FilePath = mUrl_FilePath;
        this.mContentType = mContentType;
        this.mDuration = mDuration;
        this.mDisplayName = mDisplayName;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSubTitle() {
        return mSubTitle;
    }

    public void setmSubTitle(String mSubTitle) {
        this.mSubTitle = mSubTitle;
    }

    public String getmUrl_FilePath() {
        return mUrl_FilePath;
    }

    public void setmUrl_FilePath(String mUrl_FilePath) {
        this.mUrl_FilePath = mUrl_FilePath;
    }

    public String getmContentType() {
        return mContentType;
    }

    public void setmContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

}
