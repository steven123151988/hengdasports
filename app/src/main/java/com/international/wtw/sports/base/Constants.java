package com.international.wtw.sports.base;

public class Constants {
    // 网络相关
    public static final String BASE_URL = "http://eddy.lebole5.com/userBetting/";//此行链接不允许改动
//    public static final String WEB_SOCKET_URL = "http://eddy.lebole5.com/userBetting/websocket?token=%s&userid=%s";//此行链接不允许改动
    public static final String WEB_SOCKET_URL ="ws://javaadmin.lebole2.com:8868/websocket?token=%s&userid=%s";

    public static final String LOTTERY_WEBSITE = "https://pk10tv.com/";//开奖网
    public static final String BETING = "inup";
    public static final String GET_ODDS = "getinfo/odds";
    //    public static final String REGISTER = "user/signup";
    public static final String REGISTER = "user/register";
    public static final String INFORMATION_LUNBOTU = "imageupload/carousel";//轮播图信息
    public static final String INFORMATION_CENTER = "announcement/findall";//公告中心

    public static final String GAME_INFOS = "getinfo/game";
    public static final String LUNBOTU = "user/getPicturesAndAnnouncements";
    public static final String GET_VERSION_ANDROID = "android/version";
    public static final String GET_LOTTERY_HISTORY = "getinfo/getLotteryHistory";
    public static final String GET_LOTTERY_HISTORY1 = "user/getResult";
    public static final String VERIFICATIONCODE = "sms/getVerificationCode";
    public static final String SMSAMOUNT = "sms/bindMobilePhone";
    public static final String BINDMOBILEPHONE = "sms/unBindMobilePhone";
    public static final String CHANGEMOBILEPHONE = "sms/changeMobilePhone";
    public static final String CHANGELOGINPSD = "sms/changeLoginPsd";
    public static final String CHANGEPAYPSD = "sms/changePayPsd";
    //    public static final String PERSONALCENTER = "user/getPersonalCenter";
    public static final String PERSONALCENTER = "user/personalInformation";
    /***************************资金管理************************************/
    public static final String GET_PAYIN = "user/payin";          //获取用户支持的入款方式
    public static final String ONLINE_PAY = "user/online_pay";    //在线入款
    public static final String OFFLINE_PAY = "user/offline_pay";  //线下入款
    public static final String USER_INFO = "user/info";           //修改账户(银行卡)信息
    public static final String ONLINE_GET = "user/online_get";    //在线提款
    public static final String GET_RECORD = "getinfo/record";     //交易记录
    public static final String GET_MONEY = "getinfo/money";       //获取用户信息及余额
    public static final String CUSTOMER_SERVICE = "/user/getCustomerService";       //在线客服
    public static final String GAME_PROMOTION = "movable/promotions";       //优惠活动
    /***************************我的************************************/
    public static final String GETINFO_MONEY = "getinfo/money";          //余额查询
    public static final String GETINFO_ALL = "getinfo/getBettingHistory";   //所有注单
    //    public static final String GETINFO_SUMMARYBET = "getinfo/getSummaryBet";   //已结未结汇总
    public static final String GETINFO_SUMMARYBET = "lotterybill/summary";   //已结未结汇总
    //    public static final String GETINFO_MISSEDORHASCLOSED = "getinfo/getMissedOrHasClosedBet";   //已结未结详情
    public static final String GETINFO_MISSEDORHASCLOSED = "lotterybill/findlotteyBill";   //已结未结详情
    public static final String GETINFO_BET = "getinfo/bet";          //未结注单
    public static final String GETINFO_HET = "getinfo/het";          //已结注单
    public static final String USER_INFO_WO = "user/info";          //用户信息
    public static final int TIME_OUT = 10;//请求超时时间
    //    public static final String USER_SIGNIN = "user/signin";
    public static final String USER_SIGNIN = "user/login";
    public static final String USER_OUT = "user/esc";
    //    public static final String USER_SIGNDEMO = "user/signdemo";
    public static final String USER_SIGNDEMO = "users/textuserlogin";
    public static final String SYSTEM_GAMECLOSETIME = "systems/getgameclosetime";
    public static final String GET_Lottery_History = "https://wap.alcp77.com/#/trend:51";
    public static final String GET_BETTING_HISTORY = "getinfo/getBettingHistory";
    public static final String UPDATE_LOGIN_PWD = "user/updatepwd";
    public static final String UPDATE_PAY_PWD = "user/updateplypwd";


    public class LOTTERY_TYPE {
        public static final int PJ_FUNNY_8 = 1;
        public static final int GD_5_IN_11 = 2;
        public static final int PJ_PK_10 = 3;
        public static final int CJ_LOTTERY = 4;
        public static final int LUCKY_FLY_LOTTERY = 5;
        public static final int LUCKY_28_LOTTERY = 6;  //PC 蛋蛋
        public static final int GD_HAPPY_LOTTERY = 7;
        public static final int CJ_LUCKY_LOTTERY = 8;
        public static final int HK_MARK_SIX_LOTTERY = 9;
        public static final int JS_QUICK_3 = 10;
        public static final int VENICE_SPEEDBOAT = 11;//威尼斯赛艇
        public static final int ONLINE_SERVICE = 160999;
        public static final int REAL_VIDEO = 494949;
    }

    public class PREFERENTIAL_ACTIVITIES {
        public static final int PREFERENTIAL_ONE = 919191;
        public static final int PREFERENTIAL_TWO = 929292;
        public static final int PREFERENTIAL_THREE = 939393;
    }

    public class PK_10_PLAY_TYPE {
        public static final int DOUBLE_SIDE = 0x10;
        public static final int GOLD_SILVER_COMBINE = 0x20;
        public static final int ONE_TO_FIVE = 0x30;
        public static final int SIX_TO_TEN = 0x40;
    }

    public class PK_10_PLAY_TYPE_SERVER {
        public static final int DOUBLE_SIDE = 0;
        public static final int GOLD_SILVER_COMBINE = 1;
        public static final int ONE_TO_FIVE = 2;
        public static final int SIX_TO_TEN = 3;
    }

    public class LUCKY_28_PLAY_TYPE_SERVER {
        public static final int MIX_TYPE = 0;
        public static final int SPECIAL_CODE = 1;
    }

    public class CJ_LOTTERY_PLAY_TYPE {
        public static final int DOUBLE_SIDE = 0;
        public static final int NUMBER_SIDE = 1;
        public static final int FRONT_MINDDLE_BACK = 2;
    }

    public class GD_HAPPY_PLAY_TYPE {
        public static final int DOUBLE_SIDE = 0;
        public static final int FIRST_POINT = 1;
        public static final int SECOND_POINT = 2;
        public static final int THIRD_POINT = 3;
        public static final int FORTH_POINT = 4;
        public static final int FIFTH_POINT = 5;
        public static final int SIXTH_POINT = 6;
        public static final int SEVENTH_POINT = 7;
        public static final int EIGHTH_POINT = 8;
        public static final int JOINT_MARK = 9;
        public static final int ONE_TO_FOUR = 10;
        public static final int FIVE_TO_EIGHT = 11;
    }

    public static String getErrorCodeInfo(String errorCode) {
        String info = "";
        switch (errorCode) {
            case "2001":
                info = "密码错误";
                break;
            case "2002":
                info = "用户不存在";
                break;
            case "2003":
                info = "操作失败";
                break;
            case "2004":
                info = "账户不符合规范，或存在";
                break;
            case "2005":
                info = "账户被冻结或停用";
                break;
            case "2006":
                info = "操作成功";
                break;
            case "2007":
                info = "支付密码错误";
                break;
            case "2008":
                info = "用户名: 字母或者数字组合，必须以字母开头，6~15位";
                break;
            case "2009":
                info = "密码 : 字母或者数字组合，6~15位";
                break;
            case "2010":
                info = "取款密码: 必须4位数字";
                break;
            case "2011":
                info = "用户名 : 不允许同名注册";
                break;
            case "3001":
                info = "未匹配到游戏";
                break;
            case "3002":
                info = "未匹配到玩法";
                break;
            case "3003":
                info = "缺失参数必填参数";
                break;
            case "3004":
                info = "PC蛋蛋大单大双小单小双一期只能下注一种";
                break;
            case "4001":
                info = "您的账号已失效，请重新登录";
                break;
            case "5001":
                info = "下注项为空";
                break;

            case "5002":
                info = "游戏封盘";
                break;

            case "5003":
                info = "下注期数错误";
                break;

            case "5004":
                info = "金额不足";
                break;
            case "5005":
                info = "下注金额错误";
                break;
            case "5006":
                info = "操作频繁，请稍后重试";
                break;
            case "5007":
                info = "存在未审核订单";
                break;
            case "6001":
                info = "下注数量少于规则最小投注数量";
                break;

            case "6002":
                info = "下注数量大于规则最大投注数量";
                break;
            case "6003":
                info = "金额数量少于最低限制";
                break;
            case "6004":
                info = "提款次数到达上限";
                break;
            case "7001":
                info = "操作失败";
                break;
            case "7002":
                info = "订单查询失败";
                break;
            case "7003":
                info = "额度转换失败";
                break;
            case "7004":
                info = "订单查询失败";
                break;
            case "7005":
                info = "验证码错误";
                break;
            case "8001":
                info = "该用户已经验证手机号并领取了红包";
                break;
            case "8002":
                info = "短信发送失败";
                break;
        }

        return info;
    }
}
