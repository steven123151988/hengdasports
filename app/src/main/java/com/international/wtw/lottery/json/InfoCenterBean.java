package com.international.wtw.lottery.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/4.
 */

public class InfoCenterBean implements Serializable {


    /**
     * httpCode : 200
     * response : [{"isNewRecord":true,"content":"公告1","updatetime":1508557719},{"isNewRecord":true,"content":"公告2","updatetime":1508557719},{"isNewRecord":true,"content":"公告3","updatetime":1508557719},{"isNewRecord":true,"content":"公告4","updatetime":1508557719},{"isNewRecord":true,"content":"公告5","updatetime":1508557719},{"isNewRecord":true,"content":"公告6","updatetime":1508557719},{"isNewRecord":true,"content":"556+56+","updatetime":1508557719},{"isNewRecord":true,"content":"1232","updatetime":1508557719},{"isNewRecord":true,"content":"112","updatetime":1508557719}]
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

    public static class ResponseBean  implements Serializable{
        /**
         * isNewRecord : true
         * content : 公告1
         * updatetime : 1508557719
         */

        private boolean isNewRecord;
        private String content;
        private int updatetime;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }
    }
}
