package com.international.wtw.lottery.model;

public class SocketBase<T> {


    /**
     * status : 200
     * informationCode : 001
     * payload : 0.0
     */

    public int status;
    public String informationCode;
    public T payload;
}
