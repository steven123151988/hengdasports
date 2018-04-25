package com.international.wtw.sports.utils;

import com.international.wtw.sports.json.TimeInfoBean;

import junit.framework.TestCase;

/**
 * Created by XiaoXin on 2017/9/20.
 * 描述：
 */
public class LotteryUtilTest extends TestCase {

    public void testStringCompare() throws Exception {
        String json = "{\"next\":{\"round\":\"20171003-024\",\"endtime\":1506994940,\"closetime\":1506994740,\"timestap\":1506994780},\"last\":{\"round\":\"20171003-023\",\"endtime\":null,\"number\":[\"6\",\"5\",\"6\",\"7\",\"8\"]},\"lcurrency\":0}";
        TimeInfoBean infoBean = JsonUtil.fromJson(json, TimeInfoBean.class);
        setLotteryInfo(infoBean);
    }

    private void setLotteryInfo(TimeInfoBean data) {
        TimeInfoBean.NextBean nextBean = data.getNext();
        TimeInfoBean.LastBean lastBean = data.getLast();
        //下期开奖数据
        String nextRound = nextBean.getRound();
        String lastRound = lastBean.getRound();

        int endSeconds = (int) (nextBean.getEndtime() - nextBean.getTimestap());
        int closeSeconds = (int) (nextBean.getClosetime() - nextBean.getTimestap());

        System.out.println("是否封盘: " + (nextBean.isclose() != null));

        //是否封盘
        Boolean isClosed = closeSeconds <= 0 || /*nextBean.isclose() != null ? nextBean.isclose() : */Boolean.FALSE;

        System.out.println("是否封盘: " + isClosed);
    }

    public void testCombinationCount() throws Exception {
        int count = LotteryUtil.get().getCombination(6, 3);
        System.out.println("组合数有: " + count + " 种");
    }
}