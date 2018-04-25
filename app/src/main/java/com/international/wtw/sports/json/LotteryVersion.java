package com.international.wtw.sports.json;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */

public class LotteryVersion extends BaseModel {


    /**
     * httpCode : 200
     * response : [{"forcedupdate":"0","versionnum":"2.0","versionurl":"http://182.16.115.114:8093/apk/sd.apk"}]
     */

    private int httpCode;
    private List<ResponseBean> response;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * forcedupdate : 0
         * versionnum : 2.0
         * versionurl : http://182.16.115.114:8093/apk/sd.apk
         */

        private String forcedupdate;
        private String versionnum;
        private String versionurl;

        public String getForcedupdate() {
            return forcedupdate;
        }

        public void setForcedupdate(String forcedupdate) {
            this.forcedupdate = forcedupdate;
        }

        public String getVersionnum() {
            return versionnum;
        }

        public void setVersionnum(String versionnum) {
            this.versionnum = versionnum;
        }

        public String getVersionurl() {
            return versionurl;
        }

        public void setVersionurl(String versionurl) {
            this.versionurl = versionurl;
        }
    }
}
