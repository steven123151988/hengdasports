package com.international.wtw.sports.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 18Steven on 2017/9/22.轮播图的解析类
 */

public class LunbotuBean implements Serializable {


    /**
     * httpCode : 200
     * response : [{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/15004018694575715d54606c30.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/20171114.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/20171114.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/2.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/1.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/3.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/5.jpg"},{"isNewRecord":true,"imageUrl":"http://182.16.62.242:8093/image/4.jpg"}]
     * parameter : www.bai.com
     */

    private int httpCode;
    private String parameter;
    private List<ResponseBean> response;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * isNewRecord : true
         * imageUrl : http://182.16.62.242:8093/image/15004018694575715d54606c30.jpg
         */

        private boolean isNewRecord;
        private String imageUrl;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
