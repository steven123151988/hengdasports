package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/10/6.
 */

public class PreferentialProBean {

    private boolean isNewRecord;
    private String title;
    private String imageurl;
    private String weburl;

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public PreferentialProBean() {
        super();
    }

    public PreferentialProBean(boolean isNewRecord, String title, String imageurl, String weburl) {
        this.isNewRecord = isNewRecord;
        this.title = title;
        this.imageurl = imageurl;
        this.weburl = weburl;
    }

    @Override
    public String toString() {
        return "PreferentialProBean{" +
                "isNewRecord=" + isNewRecord +
                ", title='" + title + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", weburl='" + weburl + '\'' +
                '}';
    }
}
