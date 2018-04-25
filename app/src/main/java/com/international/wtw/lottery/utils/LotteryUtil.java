package com.international.wtw.lottery.utils;

import android.support.annotation.NonNull;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.json.BetTypeItem;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.OddsItem;
import com.international.wtw.lottery.json.OddsModel;
import com.international.wtw.lottery.json.TypeTitle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoXin on 2017/9/19.
 * 描述：下注页面工具类
 */

public class LotteryUtil {

    /**
     * 下注方式一:适用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
     * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
     */
    public static final int BET_TYPE_ONE = 0x01;
    /**
     * 下注方式二:适用于 广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）
     */
    public static final int BET_TYPE_TWO = 0x02;
    /**
     * 下注方式三:适用于（过关 ，合肖，生肖连，尾数连，全不中）
     */
    public static final int BET_TYPE_THREE = 0x03;
    /**
     * 下注页面RecyclerView总宽度
     */
    public static final int TOTAL_SPAN_SIZE = 60;

    private LotteryUtil() {
    }

    private static class SingletonHolder {

        private static final LotteryUtil instance = new LotteryUtil();
    }

    public static LotteryUtil get() {
        return SingletonHolder.instance;
    }

    public String getGameName(int gameCode) {
        String titles = "";
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_FUNNY_8://北京快乐8
                titles = "北京快乐8";
                break;
            case Constants.LOTTERY_TYPE.GD_5_IN_11://广东11选5
                titles = "广东11选5";
                break;
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
                titles = "北京PK拾";
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
                titles = "重庆时时彩";
                break;
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                titles = "幸运飞艇";
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
                titles = "广东快乐十分";
                break;
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                titles = "重庆幸运农场";
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                titles = "香港六合彩";
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                titles = "PC蛋蛋";
                break;
            case Constants.LOTTERY_TYPE.JS_QUICK_3://江苏骰宝(快3)
                titles = "江苏骰宝(快3)";
                break;
        }
        return titles;
    }

    public Map<Integer, String> getAllOtherGames(int gameCode) {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(Constants.LOTTERY_TYPE.PJ_PK_10, "北京PK拾");
        map.put(Constants.LOTTERY_TYPE.CJ_LOTTERY, "重庆时时彩");
        map.put(Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY, "香港六合彩");
        map.put(Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY, "幸运飞艇");
        map.put(Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY, "PC蛋蛋");
        map.put(Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY, "广东快乐十分");
        map.put(Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY, "重庆幸运农场");
        map.put(Constants.LOTTERY_TYPE.JS_QUICK_3, "江苏骰宝(快3)");
        map.remove(gameCode);
        return map;
    }

    public List<TypeTitle> getTabTitle(int gameCode) {
        List<TypeTitle> list = new ArrayList<>();
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
                list.add(new TypeTitle(0, "两面盘"));
                list.add(new TypeTitle(1, "冠亚组合"));
                list.add(new TypeTitle(2, "一~五名"));
                list.add(new TypeTitle(3, "六~十名"));
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
                list.add(new TypeTitle(0, "两面盘"));
                list.add(new TypeTitle(1, "数字盘"));
                list.add(new TypeTitle(2, "前、中、后"));
                break;
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                list.add(new TypeTitle(0, "两面盘"));
                list.add(new TypeTitle(1, "冠亚组合"));
                list.add(new TypeTitle(2, "一~五名"));
                list.add(new TypeTitle(3, "六~十名"));
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
                list.add(new TypeTitle(0, "两面盘"));
                list.add(new TypeTitle(1, "一~四球"));
                list.add(new TypeTitle(2, "五~八球"));
                list.add(new TypeTitle(3, "连码"));
                break;
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                list.add(new TypeTitle(0, "两面盘"));
                list.add(new TypeTitle(1, "一~四球"));
                list.add(new TypeTitle(2, "五~八球"));
                list.add(new TypeTitle(3, "连码"));
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                list.add(new TypeTitle(0, "特码"));
                list.add(new TypeTitle(1, "正码"));
                list.add(new TypeTitle(2, "正码特"));
                list.add(new TypeTitle(3, "正码1~6"));
                list.add(new TypeTitle(4, "连码"));
                list.add(new TypeTitle(5, "半波"));
                list.add(new TypeTitle(6, "一肖/尾数"));
                list.add(new TypeTitle(7, "特码生肖"));
                list.add(new TypeTitle(8, "合肖"));
                list.add(new TypeTitle(9, "生肖连"));
                list.add(new TypeTitle(10, "尾数连"));
                list.add(new TypeTitle(11, "全不中"));
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                list.add(new TypeTitle(0, "混色和波色"));
                list.add(new TypeTitle(1, "特码"));
                break;
            case Constants.LOTTERY_TYPE.JS_QUICK_3://PC蛋蛋
                list.add(new TypeTitle(0, "大小骰宝"));
                break;
        }
        return list;
    }

    private int getItemBg0(String number) {
        int num = Integer.valueOf(number);
        switch (num) {
            case 1:
                return R.mipmap.img_pk10_1;
            case 2:
                return R.mipmap.img_pk10_2;
            case 3:
                return R.mipmap.img_pk10_3;
            case 4:
                return R.mipmap.img_pk10_4;
            case 5:
                return R.mipmap.img_pk10_5;
            case 6:
                return R.mipmap.img_pk10_6;
            case 7:
                return R.mipmap.img_pk10_7;
            case 8:
                return R.mipmap.img_pk10_8;
            case 9:
                return R.mipmap.img_pk10_9;
            default:
                return R.mipmap.img_pk10_10;

        }
    }

    private int getItemBg1(String num) {
        int number = Integer.valueOf(num);
        switch (number) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                return R.mipmap.img_mark_six_bg_red;
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                return R.mipmap.img_mark_six_bg_blue;
            default:
                return R.mipmap.img_mark_six_bg_green;
        }
    }

    private int getItemBg2(String num) {
        int number = Integer.valueOf(num);
        switch (number) {
            case 19:
            case 20:
                return R.mipmap.img_mark_six_bg_red;
        }
        return R.mipmap.img_lottery_common_bg;
    }

    private int getLuckyFarmBg(String num) {
        int number = Integer.valueOf(num);
        switch (number) {
            case 1:
                return R.mipmap.img_lucky_farm_1;
            case 2:
                return R.mipmap.img_lucky_farm_2;
            case 3:
                return R.mipmap.img_lucky_farm_3;
            case 4:
                return R.mipmap.img_lucky_farm_4;
            case 5:
                return R.mipmap.img_lucky_farm_5;
            case 6:
                return R.mipmap.img_lucky_farm_6;
            case 7:
                return R.mipmap.img_lucky_farm_7;
            case 8:
                return R.mipmap.img_lucky_farm_8;
            case 9:
                return R.mipmap.img_lucky_farm_9;
            case 10:
                return R.mipmap.img_lucky_farm_10;
            case 11:
                return R.mipmap.img_lucky_farm_11;
            case 12:
                return R.mipmap.img_lucky_farm_12;
            case 13:
                return R.mipmap.img_lucky_farm_13;
            case 14:
                return R.mipmap.img_lucky_farm_14;
            case 15:
                return R.mipmap.img_lucky_farm_15;
            case 16:
                return R.mipmap.img_lucky_farm_16;
            case 17:
                return R.mipmap.img_lucky_farm_17;
            case 18:
                return R.mipmap.img_lucky_farm_18;
            case 19:
                return R.mipmap.img_lucky_farm_19;
            case 20:
                return R.mipmap.img_lucky_farm_20;
        }
        return 0;
    }

    public int getPCSumBg(String sum) {
        int number = Integer.valueOf(sum);
        switch (number) {
            case 3:
            case 6:
            case 9:
            case 12:
            case 15:
            case 18:
            case 21:
            case 24:
                return R.mipmap.img_mark_six_bg_red;
            case 1:
            case 4:
            case 7:
            case 10:
            case 16:
            case 19:
            case 22:
            case 25:
                return R.mipmap.img_mark_six_bg_green;
            case 2:
            case 5:
            case 8:
            case 11:
            case 17:
            case 20:
            case 23:
            case 26:
                return R.mipmap.img_mark_six_bg_blue;
            default:
                return R.drawable.shape_dot_grey;
        }
    }

    private int getQuick3Bg(String number) {
        switch (number) {
            case "1":
                return R.drawable.ic_dice_1point;
            case "2":
                return R.drawable.ic_dice_2point;
            case "3":
                return R.drawable.ic_dice_3point;
            case "4":
                return R.drawable.ic_dice_4point;
            case "5":
                return R.drawable.ic_dice_5point;
            case "6":
                return R.drawable.ic_dice_6point;
        }
        return 0;
    }

    public int getBackgroundRes(int gameCode, String number) {
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                return getItemBg0(number);
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩:
                return getItemBg1(number);
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
                return getItemBg2(number);
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                return getLuckyFarmBg(number);
            case Constants.LOTTERY_TYPE.JS_QUICK_3://江苏骰宝(快3)
                return getQuick3Bg(number);
            default:
                return R.mipmap.img_lottery_common_bg;
        }
    }

    public String getTypeCode(int gameCode, int position, int selectedId) {
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
                switch (position) {
                    case 0:
                        return "17";
                    case 1:
                        return "18";
                    case 2:
                        return "4421";
                    case 3:
                        return "4422";
                }
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
                switch (position) {
                    case 0:
                        return "20";
                    case 1:
                        return "21";
                    case 2:
                        return "000";
                }
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                switch (position) {
                    case 0:
                        return "111";
                    case 1:
                        return "1143";
                }
                break;
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                switch (position) {
                    case 0:
                        return "27";
                    case 1:
                        return "28";
                    case 2:
                        return "4423";
                    case 3:
                        return "4424";
                }
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
                switch (position) {
                    case 0:
                        return "33";
                    case 1:
                        return "4425";
                    case 2:
                        return "4426";
                    case 3:
                        return "43";
                }
                break;
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                switch (position) {
                    case 0:
                        return "46";
                    case 1:
                        return "4427";
                    case 2:
                        return "4428";
                    case 3:
                        return "56";
                }
                break;
            case Constants.LOTTERY_TYPE.JS_QUICK_3:
                return "72";
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                switch (position) {
                    case 0://特码
                        switch (selectedId) {
                            case 0://特B
                                return "2251";
                            case 1://特A
                                return "2250";
                        }
                        break;
                    case 1://正码
                        switch (selectedId) {
                            case 0://正A
                                return "2383";
                            case 1://正B
                                return "2384";
                        }
                        break;
                    case 2://正特码
                        switch (selectedId) {
                            case 0://正1特
                                return "2490";
                            case 1://正2特
                                return "2491";
                            case 2://正3特
                                return "2492";
                            case 3://正4特
                                return "2493";
                            case 4://正5特
                                return "2494";
                            case 5://正6特
                                return "2495";
                        }
                        break;
                    case 3://正码1-6
                        return "62";
                    case 4://连码
                        return "64";
                    case 5://半波
                        return "65";
                    case 6://一肖/尾数
                        return "66";
                    case 7://特码生肖
                        return "67";
                    case 8://合肖
                        switch (selectedId) {
                            case 0://二肖
                                return "3040";
                            case 1://三肖
                                return "3041";
                            case 2://四肖
                                return "3042";
                            case 3://五肖
                                return "3043";
                            case 4://六肖
                                return "3044";
                        }
                    case 9://生肖连
                        switch (selectedId) {
                            case 0://二肖连中
                                return "3105";
                            case 1://三肖连中
                                return "3106";
                            case 2://四肖连中
                                return "3107";
                            case 3://五肖连中
                                return "3108";
                            case 4://二肖连不中
                                return "3109";
                            case 5://三肖连不中
                                return "3110";
                            case 6://四肖连不中
                                return "3111";
                        }
                    case 10://尾数连
                        switch (selectedId) {
                            case 0://二尾连中
                                return "3196";
                            case 1://三尾连中
                                return "3197";
                            case 2://四尾连中
                                return "3198";
                            case 3://二尾连不中
                                return "3199";
                            case 4://三尾连不中
                                return "3200";
                            case 5://四尾连不中
                                return "3201";
                        }
                        break;
                    case 11://全不中
                        switch (selectedId) {
                            case 0://五不中
                                return "3274";
                            case 1://六不中
                                return "3275";
                            case 2://七不中
                                return "3276";
                            case 3://八不中
                                return "3277";
                            case 4://九不中
                                return "3278";
                            case 5://十不中
                                return "3279";
                            case 6://十一不中
                                return "3280";
                            case 7://十二不中
                                return "3281";
                        }
                }
                break;
        }
        return null;
    }

    public int getBetType(int gameCode, int position) {
        int type = BET_TYPE_ONE;
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                if (position == 3) {//连码
                    type = BET_TYPE_TWO;
                }
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                switch (position) {
                    case 4://连码
                        type = BET_TYPE_TWO;
                        break;
                    case 8://合肖
                    case 9://生肖连
                    case 10://尾数连
                    case 11://全不中
                        type = BET_TYPE_THREE;
                        break;
                }
                break;
        }
        return type;
    }

    public List<BetTypeItem> getTypeData(int gameCode, int position) {
        List<BetTypeItem> list = new ArrayList<>();
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                if (position == 3) {//连码
                    list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "任选二", TOTAL_SPAN_SIZE / 4, true));
                    //list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, null, "选二直连", "封盘", null, TOTAL_SPAN_SIZE / 4, false));
                    //list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, null, "选二连组", "封盘", null, TOTAL_SPAN_SIZE / 4, false));
                    list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "任选三", TOTAL_SPAN_SIZE / 4, false));
                    //list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, null, "选三前直", "封盘", null, TOTAL_SPAN_SIZE / 4, true));
                    //list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, null, "选三连组", "封盘", null, TOTAL_SPAN_SIZE / 4, false));
                    list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "任选四", TOTAL_SPAN_SIZE / 4, false));
                    list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "任选五", TOTAL_SPAN_SIZE / 4, false));
                }
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                switch (position) {
                    case 0://特码
                        list.add(new BetTypeItem(gameCode, 1, "特B", TOTAL_SPAN_SIZE / 2, true));
                        list.add(new BetTypeItem(gameCode, 2, "特A", TOTAL_SPAN_SIZE / 2));
                        break;
                    case 1://正码
                        list.add(new BetTypeItem(gameCode, 3, "正A", TOTAL_SPAN_SIZE / 2, true));
                        list.add(new BetTypeItem(gameCode, 4, "正B", TOTAL_SPAN_SIZE / 2));
                        break;
                    case 2://正特码
                        list.add(new BetTypeItem(gameCode, 5, "正1特", TOTAL_SPAN_SIZE / 3, true));
                        list.add(new BetTypeItem(gameCode, 6, "正2特", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 7, "正3特", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 8, "正4特", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 9, "正5特", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 10, "正6特", TOTAL_SPAN_SIZE / 3));
                        break;
                    case 3:
                        break;
                    case 4://连码
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "三全中", TOTAL_SPAN_SIZE / 3, true));
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "中二/中三", TOTAL_SPAN_SIZE / 3, false));
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "二全中", TOTAL_SPAN_SIZE / 3, false));
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "中特/中二", TOTAL_SPAN_SIZE / 3, false));
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "四中一", TOTAL_SPAN_SIZE / 3, false));
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO, "特串", TOTAL_SPAN_SIZE / 3, false));
                        break;
                    case 5:
                    case 6:
                    case 7:
                        break;
                    case 8://合肖
                        list.add(new BetTypeItem(gameCode, 17, "二肖", TOTAL_SPAN_SIZE / 5, true));
                        list.add(new BetTypeItem(gameCode, 18, "三肖", TOTAL_SPAN_SIZE / 5));
                        list.add(new BetTypeItem(gameCode, 19, "四肖", TOTAL_SPAN_SIZE / 5));
                        list.add(new BetTypeItem(gameCode, 20, "五肖", TOTAL_SPAN_SIZE / 5));
                        list.add(new BetTypeItem(gameCode, 21, "六肖", TOTAL_SPAN_SIZE / 5));
                        break;
                    case 9://生肖连
                        list.add(new BetTypeItem(gameCode, 27, "二肖连中", TOTAL_SPAN_SIZE / 4, true));
                        list.add(new BetTypeItem(gameCode, 28, "三肖连中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 29, "四肖连中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 30, "五肖连中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 31, "二肖不连中", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 32, "三肖不连中", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 33, "四肖不连中", TOTAL_SPAN_SIZE / 3));
                        break;
                    case 10:
                        list.add(new BetTypeItem(gameCode, 34, "二尾连中", TOTAL_SPAN_SIZE / 3, true));
                        list.add(new BetTypeItem(gameCode, 35, "三尾连中", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 36, "四尾连中", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 37, "二尾不连中", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 38, "三尾不连中", TOTAL_SPAN_SIZE / 3));
                        list.add(new BetTypeItem(gameCode, 39, "四尾不连中", TOTAL_SPAN_SIZE / 3));
                        break;
                    case 11:
                        list.add(new BetTypeItem(gameCode, 40, "五全不中", TOTAL_SPAN_SIZE / 4, true));
                        list.add(new BetTypeItem(gameCode, 41, "六全不中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 42, "七全不中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 43, "八全不中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 44, "九全不中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 45, "十全不中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 46, "十一全不中", TOTAL_SPAN_SIZE / 4));
                        list.add(new BetTypeItem(gameCode, 46, "十二全不中", TOTAL_SPAN_SIZE / 4));
                        break;
                }
                break;
        }
        return list;
    }

    public List<BetTypeItem> getTypeData(int gameCode, List<OddsItem> listBeen) {
        List<BetTypeItem> list = new ArrayList<>();
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                for (int i = 0; i < 4; i++) {
                    OddsItem item = listBeen.get(i);
//                    if ("1554".equals(item.getKey()) || "1555".equals(item.getKey())
//                            || "1557".equals(item.getKey()) || "1558".equals(item.getKey())
//                            || "2083".equals(item.getKey()) || "2084".equals(item.getKey())
//                            || "2086".equals(item.getKey()) || "2087".equals(item.getKey())) {
//                        continue;
//                    }
                    if (list.size() == 0) {
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO,
                                item.getName(), TOTAL_SPAN_SIZE / 4, true, item));
                    } else {
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO,
                                item.getName(), TOTAL_SPAN_SIZE / 4, false, item));
                    }
                }
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                for (int i = 0; i < listBeen.size(); i++) {
                    OddsItem item = listBeen.get(i);
                    if (i == 0) {
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO,
                                item.getName(), TOTAL_SPAN_SIZE / 3, true, item));
                    } else {
                        list.add(new BetTypeItem(gameCode, BetTypeItem.TYPE_COMBO,
                                item.getName(), TOTAL_SPAN_SIZE / 3, false, item));
                    }
                }
                break;
        }
        return list;
    }

    public List<MultiBetItem> getBetData(int gameCode, int position, String typeCode, List<OddsModel> oddsList) {
        List<MultiBetItem> list = new ArrayList<>();
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                switch (position) {
                    case 0:
                        oddsList.remove(0);
                        addData2List(gameCode, typeCode, list, oddsList, 5, 0, MultiBetItem.CONTENT_TEXT);
                        addData2List(gameCode, typeCode, list, oddsList, 5, 5, MultiBetItem.CONTENT_TEXT);
                        break;
                    case 1:
                        for (int i = 0; i < oddsList.size(); i++) {
                            OddsModel model = oddsList.get(i);
                            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, model.name));
                            for (int j = 0; j < model.list.size(); j++) {
                                if (i == 1 && j >= 12) {
                                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                            TOTAL_SPAN_SIZE / 5, model.name, model.list.get(j)));
                                } else {
                                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                            TOTAL_SPAN_SIZE / 4, model.name, model.list.get(j)));
                                }
                            }
                        }
                        break;
                    case 2:
                    case 3:
                        addData2List(gameCode, typeCode, list, oddsList, 5, 0, MultiBetItem.CONTENT_TEXT);
                        break;
                }
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
                switch (position) {
                    case 0:
                        addData2List(gameCode, typeCode, list, oddsList, 5, 0, MultiBetItem.CONTENT_TEXT);
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(5).name.replace(" ", "")));
                        List<OddsItem> list1 = oddsList.get(5).list;
                        int size1 = list1.size();
                        for (int i = 0; i < size1; i++) {
                            if (i < 4) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, oddsList.get(5).name, list1.get(i)));
                            } else {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 3, oddsList.get(5).name, list1.get(i)));
                            }
                        }
                        break;
                    case 1:
                        addData2List(gameCode, typeCode, list, oddsList, 5, 0, MultiBetItem.CONTENT_NUM);
                        break;
                    case 2:
                        addData2List(gameCode, typeCode, list, oddsList, 3, 0, MultiBetItem.CONTENT_TEXT);
                        break;
                }
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                switch (position) {
                    case 0://两面盘
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(8).name));
                        List<OddsItem> list1 = oddsList.get(8).list;
                        int size = list1.size();
                        for (int i = 0; i < size; i++) {
                            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, oddsList.get(0).name, list1.get(i)));
                        }
                        addData2List(gameCode, typeCode, list, oddsList, 4, 0, MultiBetItem.CONTENT_TEXT);
                        addData2List(gameCode, typeCode, list, oddsList, 4, 4, MultiBetItem.CONTENT_TEXT);
                        break;
                    case 1://一~四
                    case 2://五~八
                        addData2List(gameCode, typeCode, list, oddsList, 4, 0, MultiBetItem.CONTENT_TEXT);
                        break;
                    case 3://连码
                        //list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, "连码"));
                        for (int i = 1; i <= 20; i++) {
                            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_LIAN, TOTAL_SPAN_SIZE / 4, "连码", i));
                        }
                        break;
                }
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                switch (position) {
                    case 0://特码
                    case 1://正码
                        addMarkSixNumber(gameCode, typeCode, list, oddsList.get(0));
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(1).name));
                        for (int i = 0; i < oddsList.get(1).list.size(); i++) {
                            if (i < 12) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, oddsList.get(1).name, oddsList.get(1).list.get(i)));
                            } else {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 5, oddsList.get(1).name, oddsList.get(1).list.get(i)));
                            }
                        }
                        break;
                    case 2://正码特
                        addMarkSixNumber(gameCode, typeCode, list, oddsList.get(0));
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(1).name));
                        for (int i = 0; i < oddsList.get(1).list.size(); i++) {
                            if (i < 4) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, oddsList.get(1).name, oddsList.get(1).list.get(i)));
                            } else {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 5, oddsList.get(1).name, oddsList.get(1).list.get(i)));
                            }
                        }
                        break;
                    case 3://正码1~6
                        addData2List(gameCode, typeCode, list, oddsList, 3, 0, MultiBetItem.CONTENT_TEXT);
                        addData2List(gameCode, typeCode, list, oddsList, 3, 3, MultiBetItem.CONTENT_TEXT);
                        break;
                    case 4://连码
                        //list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, "连码"));
                        for (int i = 1; i <= 49; i++) {
                            if (i <= 44) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_LIAN, TOTAL_SPAN_SIZE / 4, "连码", i));
                            } else {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_LIAN, TOTAL_SPAN_SIZE / 5, "连码", i));
                            }
                        }
                        break;
                    case 5://半波
                    case 7://特码生肖
                        for (int i = 0; i < oddsList.size(); i++) {
                            //list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(i).name));
                            List<OddsItem> list1 = oddsList.get(i).list;
                            for (int j = 0; j < list1.size(); j++) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 3, oddsList.get(0).name, list1.get(j)));
                            }
                        }
                        break;
                    case 6://一肖/尾肖
                        for (int i = 0; i < oddsList.size(); i++) {
                            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(i).name));
                            List<OddsItem> list2 = oddsList.get(i).list;
                            for (int j = 0; j < list2.size(); j++) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / (i == 0 ? 5 : 4), oddsList.get(i).name, list2.get(j)));
                            }
                        }
                        break;
                    case 8://合肖
                    case 9://生肖连
                        for (int i = 0; i < oddsList.size(); i++) {
                            //list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(i).name));
                            List<OddsItem> list2 = oddsList.get(i).list;
                            for (int j = 0; j < list2.size(); j++) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, oddsList.get(i).name, list2.get(j)));
                            }
                        }
                        break;
                    case 10:
                        //list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsList.get(0).name));
                        List<OddsItem> list2 = oddsList.get(0).list;
                        for (int i = 0; i < list2.size(); i++) {
                            if (i < 6) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 3, oddsList.get(0).name, list2.get(i)));
                            } else {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, oddsList.get(0).name, list2.get(i)));
                            }
                        }
                        break;
                    case 11:
                        addMarkSixNumber(gameCode, typeCode, list, oddsList.get(0));
                        break;
                }
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                switch (position) {
                    case 0://混合 波色
                        for (int i = 0; i < oddsList.size(); i++) {
                            OddsModel oddsBean = oddsList.get(i);
                            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsBean.name));
                            for (int j = 0; j < oddsBean.list.size(); j++) {
                                if (i == 0 && j < 8) {
                                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                            TOTAL_SPAN_SIZE / 4, oddsBean.name, oddsBean.list.get(j)));
                                } else {
                                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                            TOTAL_SPAN_SIZE / 3, oddsBean.name, oddsBean.list.get(j)));
                                }
                            }
                        }
                        break;
                    case 1://特码
                        for (int i = 0; i < oddsList.size(); i++) {
                            OddsModel oddsBean = oddsList.get(i);
                            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsBean.name));
                            for (int j = 0; j < oddsBean.list.size(); j++) {
                                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, oddsBean.name, oddsBean.list.get(j)));
                            }
                        }
                        break;
                }
                break;
            case Constants.LOTTERY_TYPE.JS_QUICK_3:
                //三军 大小
                OddsModel three = oddsList.get(0);
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, three.name));
                for (int i = 0; i < three.list.size(); i++) {
                    OddsItem oddsItem = three.list.get(i);
                    if (i < 6) {
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_NUM,
                                TOTAL_SPAN_SIZE / 4, three.name, oddsItem));
                    } else {
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                TOTAL_SPAN_SIZE / 4, three.name, oddsItem));
                    }
                }
                //围骰、全骰
                OddsModel dice = oddsList.get(1);
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, dice.name));
                for (int i = 0; i < dice.list.size(); i++) {
                    if (i < dice.list.size() - 1) {
                        OddsItem oddsItem = dice.list.get(i);
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_MULTI_NUM,
                                TOTAL_SPAN_SIZE / (i < 3 ? 3 : 4), dice.name, oddsItem));
                    } else {
                        OddsItem oddsItem = dice.list.get(i);
                        list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                                TOTAL_SPAN_SIZE / (i < 3 ? 3 : 4), dice.name, oddsItem));
                    }
                }
                //点数
                OddsModel point = oddsList.get(2);
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, point.name));
                for (int i = 0; i < point.list.size(); i++) {
                    OddsItem oddsItem = point.list.get(i);
                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_TEXT,
                            TOTAL_SPAN_SIZE / (i < 10 ? 5 : 4), point.name, oddsItem));
                }
                //长牌
                OddsModel longX = oddsList.get(3);
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, longX.name));
                for (int i = 0; i < longX.list.size(); i++) {
                    OddsItem oddsItem = longX.list.get(i);
                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_MULTI_NUM,
                            TOTAL_SPAN_SIZE / 5, longX.name, oddsItem));
                }
                //短牌
                OddsModel shortX = oddsList.get(4);
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, shortX.name));
                for (int i = 0; i < shortX.list.size(); i++) {
                    OddsItem oddsItem = shortX.list.get(i);
                    list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_MULTI_NUM,
                            TOTAL_SPAN_SIZE / 3, shortX.name, oddsItem));
                }
                break;
        }
        return list;
    }

    private void addData2List(int gameCode, String typeCode, List<MultiBetItem> list, List<OddsModel> oddsList, int columnCount, int startColumn, int contentType) {
        OddsModel oddsBean = oddsList.get(startColumn);
        int size = oddsBean.list.size();
        for (int i = 0; i < columnCount; i++) {
            list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE / columnCount,
                    oddsList.get(i + startColumn).name));
        }
        for (int i = 0; i < size * columnCount; i++) {
            list.add(new MultiBetItem(gameCode, typeCode, contentType, TOTAL_SPAN_SIZE / columnCount, oddsList.get(i % columnCount + startColumn).name,
                    oddsList.get(i % columnCount + startColumn).list.get(i / columnCount)));
        }
    }

    private void addMarkSixNumber(int gameCode, String typeCode, List<MultiBetItem> list, OddsModel oddsBean) {
        //list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.TITLE, TOTAL_SPAN_SIZE, oddsBean.name));
        for (int i = 0; i < oddsBean.list.size(); i++) {
            if (i < 44) {
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_NUM, TOTAL_SPAN_SIZE / 4, oddsBean.name, oddsBean.list.get(i)));
            } else {
                list.add(new MultiBetItem(gameCode, typeCode, MultiBetItem.CONTENT_NUM, TOTAL_SPAN_SIZE / 5, oddsBean.name, oddsBean.list.get(i)));
            }
        }
    }

    public String checkBetBeans(int gameCode, int position, int selectId, @NonNull List<MultiBetItem> selectBeans) {
        if (selectBeans.isEmpty()) {
            return ResourcesUtil.getString(R.string.select_betting_item);
        }
        String message = null;
        int size = selectBeans.size();
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                if (position == 3) {
                    switch (selectId) {
                        case 0://任选二
                            message = getBetLimitString(size, 2, 8);
                            break;
                        case 1://任选三
                            message = getBetLimitString(size, 3, 8);
                            break;
                        case 2://任选四
                            message = getBetLimitString(size, 4, 5);
                            break;
                        case 3://任选五
                            message = getBetLimitString(size, 5, 6);
                            break;
                    }
                }
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                switch (position) {
                    case 4://连码
                        switch (selectId) {
                            case 1://二全中
                            case 3://特串
                            case 5://中特/中二
                                message = getBetLimitString(size, 2, 10);
                                break;
                            case 0://三全中
                            case 4://中二/中三
                                message = getBetLimitString(size, 3, 10);
                                break;
                            case 2://四中一
                                message = getBetLimitString(size, 4, 10);
                                break;
                        }
                        break;
                    case 8://合肖
                        switch (selectId) {
                            case 0://二肖
                                if (size != 2)
                                    message = String.format(ResourcesUtil.getString(R.string.bet_just), 2);
                                break;
                            case 1://三肖
                                if (size != 3)
                                    message = String.format(ResourcesUtil.getString(R.string.bet_just), 3);
                                break;
                            case 2://四肖
                                if (size != 4)
                                    message = String.format(ResourcesUtil.getString(R.string.bet_just), 4);
                                break;
                            case 3://五肖
                                if (size != 5)
                                    message = String.format(ResourcesUtil.getString(R.string.bet_just), 5);
                                break;
                            case 4://六肖
                                if (size != 6)
                                    message = String.format(ResourcesUtil.getString(R.string.bet_just), 6);
                                break;
                        }
                        break;
                    case 9://生肖连
                        switch (selectId) {
                            case 0://二肖连中
                                message = getBetLimitString(size, 2, 8);
                                break;
                            case 1://三肖连中
                                message = getBetLimitString(size, 3, 8);
                                break;
                            case 2://四肖连中
                                message = getBetLimitString(size, 4, 8);
                                break;
                            case 3://五肖连中
                                message = getBetLimitString(size, 5, 8);
                                break;
                            case 4://二肖不连中
                                message = getBetLimitString(size, 2, 8);
                                break;
                            case 5://三肖不连中
                                message = getBetLimitString(size, 3, 8);
                                break;
                            case 6://四肖不连中
                                message = getBetLimitString(size, 4, 8);
                                break;
                        }
                        break;
                    case 10://尾数连
                        switch (selectId) {
                            case 0://二尾连中
                                message = getBetLimitString(size, 2, 8);
                                break;
                            case 1://三尾连中
                                message = getBetLimitString(size, 3, 8);
                                break;
                            case 2://四尾连中
                                message = getBetLimitString(size, 4, 8);
                                break;
                            case 3://二尾不连中
                                message = getBetLimitString(size, 2, 8);
                                break;
                            case 4://三尾不连中
                                message = getBetLimitString(size, 3, 8);
                                break;
                            case 5://四尾不连中
                                message = getBetLimitString(size, 4, 8);
                                break;
                        }
                        break;
                    case 11://全不中
                        switch (selectId) {
                            case 0://五不中
                                message = getBetLimitString(size, 5, 10);
                                break;
                            case 1://六不中
                                message = getBetLimitString(size, 6, 10);
                                break;
                            case 2://七不中
                                message = getBetLimitString(size, 7, 10);
                                break;
                            case 3://八不中
                                message = getBetLimitString(size, 8, 10);
                                break;
                            case 4://九不中
                                message = getBetLimitString(size, 9, 10);
                                break;
                            case 5://十不中
                                message = getBetLimitString(size, 10, 12);
                                break;
                            case 6://十一不中
                                message = getBetLimitString(size, 11, 13);
                                break;
                            case 7://十二不中
                                message = getBetLimitString(size, 12, 15);
                                break;
                        }
                        break;
                }
                break;
        }
        return message;
    }

    private String getBetLimitString(int size, int min, int max) {
        if (size < min || size > max) {
            return String.format(ResourcesUtil.getString(R.string.bet_limit), min, max);
        }
        return null;
    }

    public int getTotalBetNum(int gameCode, int position, int selectId, int size) {
        int total = size;
        switch (gameCode) {
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                if (position == 3) {
                    switch (selectId) {
                        case 0://任选二
                            total = getCombination(size, 2);
                            break;
                        case 1://任选三
                            total = getCombination(size, 3);
                            break;
                        case 2://任选四
                            total = getCombination(size, 4);
                            break;
                        case 3://任选五
                            total = getCombination(size, 5);
                            break;
                    }
                }
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                switch (position) {
                    case 4://连码
                        switch (selectId) {
                            case 1://二全中
                            case 3://特串
                            case 5://中特/中二
                                total = getCombination(size, 2);
                                break;
                            case 0://三全中
                            case 4://中二/中三
                                total = getCombination(size, 3);
                                break;
                            case 2://四中一
                                total = getCombination(size, 4);
                                break;
                        }
                        break;
                    case 8://合肖
                        switch (selectId) {
                            case 0://二肖
                                total = getCombination(size, 2);
                                break;
                            case 1://三肖
                                total = getCombination(size, 3);
                                break;
                            case 2://四肖
                                total = getCombination(size, 4);
                                break;
                            case 3://五肖
                                total = getCombination(size, 5);
                                break;
                            case 4://六肖
                                total = getCombination(size, 6);
                                break;
                        }
                        break;
                    case 9://生肖连
                        switch (selectId) {
                            case 0://二肖连中
                                total = getCombination(size, 2);
                                break;
                            case 1://三肖连中
                                total = getCombination(size, 3);
                                break;
                            case 2://四肖连中
                                total = getCombination(size, 4);
                                break;
                            case 3://五肖连中
                                total = getCombination(size, 5);
                                break;
                            case 4://二肖不连中
                                total = getCombination(size, 2);
                                break;
                            case 5://三肖不连中
                                total = getCombination(size, 3);
                                break;
                            case 6://四肖不连中
                                total = getCombination(size, 4);
                                break;
                        }
                        break;
                    case 10://尾数连
                        switch (selectId) {
                            case 0://二尾连中
                                total = getCombination(size, 2);
                                break;
                            case 1://三尾连中
                                total = getCombination(size, 3);
                                break;
                            case 2://四尾连中
                                total = getCombination(size, 4);
                                break;
                            case 3://二尾不连中
                                total = getCombination(size, 2);
                                break;
                            case 4://三尾不连中
                                total = getCombination(size, 3);
                                break;
                            case 5://四尾不连中
                                total = getCombination(size, 4);
                                break;
                        }
                        break;
                    case 11://全不中
                        switch (selectId) {
                            case 0://五不中
                                total = getCombination(size, 5);
                                break;
                            case 1://六不中
                                total = getCombination(size, 6);
                                break;
                            case 2://七不中
                                total = getCombination(size, 7);
                                break;
                            case 3://八不中
                                total = getCombination(size, 8);
                                break;
                            case 4://九不中
                                total = getCombination(size, 9);
                                break;
                            case 5://十不中
                                total = getCombination(size, 10);
                                break;
                            case 6://十一不中
                                total = getCombination(size, 11);
                                break;
                            case 7://十二不中
                                total = getCombination(size, 12);
                                break;
                        }
                        break;
                }
                break;
        }
        return total;
    }

    /**
     * 从n个数中取k个数出来的组合有多少种
     * 按排列与组合公式 count = n!/(k!*(n-k)!)
     */
    public int getCombination(int n, int k) {
        if (n < k) {
            return 0;
        } else if (k == 0 || n == k) {
            return 1;
        }
        int count = 1;
        for (int i = n; i > k; i--) {
            count *= i;
        }
        for (int i = 1; i <= n - k; i++) {
            count /= i;
        }
        return count;
    }

    public String getZodiac(int number) {
        switch (number % 12) {
            case 10:
                return "鼠";
            case 9:
                return "牛";
            case 8:
                return "虎";
            case 7:
                return "兔";
            case 6:
                return "龙";
            case 5:
                return "蛇";
            case 4:
                return "马";
            case 3:
                return "羊";
            case 2:
                return "猴";
            case 1:
                return "鸡";
            case 0:
                return "狗";
            default:
                return "猪";
        }
    }

    public String getYuXiaXie(String number) {
        switch (number) {
            case "1":
                return "鱼";
            case "2":
                return "虾";
            case "3":
                return "葫芦";
            case "4":
                return "金钱";
            case "5":
                return "蟹";
            case "6":
                return "鸡";
            default:
                return "";
        }
    }
}
