package com.international.wtw.sports.json;

import java.io.Serializable;

/**
 * Created by Abin on 2017/9/9.
 * 描述：Model基类 其他model需继承该类
 */

public class BaseModel implements Serializable {

    /**
     * 状态码
     */
    protected int code;

    /**
     * 错误信息
     */
    protected String msg;

    protected String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
