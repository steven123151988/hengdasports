package com.international.wtw.sports.api;

/**
 * Created by XiaoXin on 2017/9/22.
 * 描述：请求接口数据时, 返回错误时 根据错误码抛出的异常
 */
public class ApiException extends RuntimeException {

    private String msgCode;

    public ApiException(String msgCode) {
        super(getErrorMessage(msgCode));
        this.msgCode = msgCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    /**
     * 通过错误码获取错误信息
     *
     * @param msgCode 错误码
     */
    private static String getErrorMessage(String msgCode) {
        String msg = "未定义错误";
        switch (msgCode) {
            case "2001":
                msg = "密码错误";
                break;
            case "2002":
                msg = "用户不存在";
                break;
            case "2003":
                msg = "操作失败";
                break;
            case "2004":
                msg = "账户不符合规范或者已经存在";
                break;
            case "2005":
                msg = "账户被冻结或停用";
                break;
            case "2006":
                msg = "操作成功";
                break;
            case "2007":
                msg = "支付密码错误";
                break;
            case "2008":
                msg = "用户名: 字母或者数字组合，必须以字母开头，6~15位 ";
                break;
            case "2009":
                msg = "密码: 字母或者数字组合，6~15位";
                break;
            case "2010":
                msg = "支付密码: 必须4位数字";
                break;
            case "2011":
                msg = "该用户名已存在";
                break;
            case "3001":
                msg = "未匹配到游戏";
                break;
            case "3002":
                msg = "未匹配到玩法";
                break;
            case "3003":
                msg = "缺少必要的参数";
                break;
            case "3004":
                msg = "PC蛋蛋大单大双小单小双一期只能下注一种";
                break;
            case "4001":
                msg = "您的账号已失效，请重新登录";
                break;
            case "5001":
                msg = "下注项为空或金额不正确";
                break;
            case "5002":
                msg = "游戏封盘";
                break;
            case "5003":
                msg = "下注期数错误";
                break;
            case "5004":
                msg = "金额不足不能下注";
                break;
            case "5005":
                msg = "下注金额错误";
                break;
            case "5006":
                msg = "操作频繁，请稍后重试";
                break;
            case "5007":
                msg = "存在未审核订单";
                break;
            case "6001":
                msg = "下注数量少于规则最小投注数量";
                break;
            case "6002":
                msg = "下注数量大于规则最大投注数量";
                break;
            case "6003":
                msg = "金额数量少于最低限制";
                break;
            case "6004":
                msg = "提款次数到达上限";
                break;
            case "60041":
                msg = "您还有未处理的订单,请耐心等候处理完毕后再提交";
                break;
            case "7001":
                msg = "操作失败";
                break;
            case "7002":
                msg = "订单查询失败";
                break;
            case "7003":
                msg = "额度转换失败";
                break;
            case "7004":
                msg = "订单查询失败";
                break;
            case "7005":
                msg = "验证码错误";
                break;
            case "8001":
                msg = "该用户已经验证手机号并领取了红包";
                break;
            case "8002":
                msg = "短信发送失败";
                break;
            case "8003":
                msg = "验证码错误";
                break;
            case "8004":
                msg = "此网站未开通短信功能";
                break;
        }
        return msg;
    }
}
