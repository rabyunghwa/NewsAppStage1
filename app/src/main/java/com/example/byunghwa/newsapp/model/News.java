package com.example.byunghwa.newsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    private String id;
    private String type;
    private String sectionID;
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webURL;
    private String apiURL;
    private boolean isHosted;
    private String pillarID;
    private String pillarName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public boolean isHosted() {
        return isHosted;
    }

    public void setHosted(boolean hosted) {
        isHosted = hosted;
    }

    public String getPillarID() {
        return pillarID;
    }

    public void setPillarID(String pillarID) {
        this.pillarID = pillarID;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public static Creator<News> getCREATOR() {
        return CREATOR;
    }

    public News(Parcel in) {
        id = in.readString();
        type = in.readString();
        sectionID = in.readString();
        sectionName = in.readString();
        webPublicationDate = in.readString();
        webTitle = in.readString();
        webURL = in.readString();
        apiURL = in.readString();
        isHosted = in.readByte() != 0;
        pillarID = in.readString();
        pillarName = in.readString();
    }

    public News() {

    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(sectionID);
        dest.writeString(sectionName);
        dest.writeString(webPublicationDate);
        dest.writeString(webTitle);
        dest.writeString(webURL);
        dest.writeString(apiURL);
        dest.writeByte((byte) (isHosted ? 1 : 0));
        dest.writeString(pillarID);
        dest.writeString(pillarName);
    }
}
