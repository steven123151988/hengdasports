package com.international.wtw.lottery.model;

import com.google.gson.annotations.SerializedName;

public class Result<T> {

    @SerializedName("httpCode")
    private int status;
    private int total;
    private String message;
    private T response;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMessage() {
        switch (status) {
            case 200:
                message = "请求成功";
                break;
            case 201:
                message = "更新成功";
                break;
            case 500:
                message = "空指针异常";
                break;
            case 501:
                message = "授权失败";
                break;
            case 502:
                message = "参数错误";
                break;
            case 503:
                message = "更新失败";
                break;
            case 504:
                message = "SQL异常";
                break;
            case 505:
                message = "新增成功";
                break;
            case 506:
                message = "新增失败";
                break;
            case 507:
                message = "删除成功";
                break;
            case 508:
                message = "删除失败";
                break;
            case 509:
                message = "登出成功";
                break;
            case 510:
                message = "密码不匹配";
                break;
            case 511:
                message = "没有绑定银行卡";
                break;
            case 512:
                message = "旧密码错误";
                break;
            case 514:
                message = "获取数据失败";
                break;
            case 515:
                message = "登陆成功";
                break;
            case 516:
                message = "登陆失败";
                break;
            case 517:
                message = "用户余额不足";
                break;
            case 518:
                message = "系统内部错误";
                break;
            case 519:
                message = "银行卡号已经存在";
                break;
            case 520:
                message = "查找不到数据";
                break;
            case 521:
                message = "该账号已存在";
                break;
            case 523:
                message = "登陆失败,用户已在线";
                break;
            case 526:
                message = "投注成功";
                break;
            case 527:
                message = "投注失败";
                break;
            case 528:
                message = "添加成功";
                break;
            case 529:
                message = "添加失败";
                break;
            case 530:
                message = "提现成功";
                break;
            case 531:
                message = "提现失败";
                break;
            case 532:
                message = "注册成功";
                break;
            case 533:
                message = "注册失败";
                break;
            case 534:
                message = "账号可用";
                break;
            case 535:
                message = "usersId不能为空";//sessionId不能为空
                break;
            case 536:
                message = "您的账号已失效，请重新登录";//usersId不能为空
                break;
            case 537:
                message = "充值失败";
                break;
            case 538:
                message = "用户已被禁用";
                break;
            case 539:
                message = "游戏维护中";
                break;
            case 540:
                message = "该游戏已封盘，请稍后再试";
                break;
            case 541:
                message = "下注金额小于单注最小金额";
                break;
            case 542:
                message = "下注金额大于单注最大金额";
                break;
            case 543:
                message = "下注金总额大于单期最高限额";
                break;
            case 544:
                message = "两面盘玩法不允许对买";
                break;
            case 545:
                message = "存在未审核订单情况下不允许入款";
                break;
            case 552:
                message = "登陆失败，账号或密码错误";
                break;
            case 553:
                message = "登陆失败，用户已在线";
                break;
            case 554:
                message = "登陆失败，用户不存在";
                break;
            case 2001:
                message = "登陆失败,账号或密码错误";
                break;
            case 2002:
                message = "登陆失败,用户不存在";
                break;
            case 2004:
                message = "该账号已存在";
                break;
            case 2007:
                message = "支付密码错误";
                break;
            case 4001:
                message = "您的账号已失效，请重新登录";
                break;
            case 4002:
                message = "当前用户已连接WebSocket";
                break;
        }
        return message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}