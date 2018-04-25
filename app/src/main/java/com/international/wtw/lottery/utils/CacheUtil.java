package com.international.wtw.lottery.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.international.wtw.lottery.json.Token;

import java.util.List;


/**
 * 缓存工具类，用于缓存各类数据
 */
public class CacheUtil {


    ACache cache;

    public CacheUtil(Context context) {
        cache = ACache.get(context);
    }

    //--- token ------------------------------------------------------------------------------------

    public void saveToken(@NonNull Token token){
        cache.put("token", token);
    }

    public Token getToken(){
        return (Token) cache.getAsObject("token");
    }

    public void clearToken(){
        cache.remove("token");
    }

    //--- login info -------------------------------------------------------------------------------
    private static final String LOGIN_ACCOUNT = "loing_account";
    private static final String LOGIN_PWD = "login_pwd";
    public void saveLoginInfo(String account,String password){
        cache.put(LOGIN_ACCOUNT,account);
        cache.put(LOGIN_PWD,password);
    }

    public void clearLoginInfo(){
        cache.remove(LOGIN_ACCOUNT);
        cache.remove(LOGIN_PWD);
    }
    public String getLoginAccount(){
        return cache.getAsString(LOGIN_ACCOUNT);
    }
    public String getLoginPwd(){
        return cache.getAsString(LOGIN_PWD);
    }

    public void saveUrlList(List<String> urlList,String fileName){
        cache.saveStorage2SDCard(urlList,fileName);
    }

    public List<String> getUrlList(String fileName){
        return cache.getStorageEntities(fileName);
    }

    public void savePagerShow(List<String> pagerShows,String fileName){
        cache.saveStorage2SDCard(pagerShows,fileName);
    }

    public List<String> getPagerShow(String fileName){
        return cache.getStorageEntities(fileName);
    }
}
