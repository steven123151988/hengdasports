package com.international.wtw.sports.json;

/**
 * Created by wuya on 2017/5/2.
 */

import java.io.Serializable;

/**
 * 令牌
 */
public class Token implements Serializable {

    private String access_token;     // 用户令牌(获取相关数据使用)
    private String token_type;       // 令牌类型
    private long expires_in;          // 过期时间
    private String refresh_token;    // 刷新令牌(获取新的令牌)
    private long created_at;          // 创建时间

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }


    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}

