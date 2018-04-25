package com.international.wtw.lottery.api;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.json.BetTypeItem;
import com.international.wtw.lottery.json.BetTypeItem_;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.MultiBetItem_;
import com.international.wtw.lottery.model.BankRes;
import com.international.wtw.lottery.model.GameModel;
import com.international.wtw.lottery.model.MineItem;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalRepository {


    private LocalRepository() {
    }

    private static class SingletonHolder {
        private static LocalRepository instance = new LocalRepository();
    }

    public static LocalRepository get() {
        return SingletonHolder.instance;
    }

    public List<GameModel> getHomeGames() {
        List<GameModel> list = new ArrayList<>();
        list.add(new GameModel(Constants.LOTTERY_TYPE.PJ_PK_10,
                "北京PK拾",
                R.drawable.img_logo_bj_pk_10));
        list.add(new GameModel(Constants.LOTTERY_TYPE.CJ_LOTTERY,
                "重庆时时彩",
                R.drawable.img_logo_cq_sscai));
        list.add(new GameModel(Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY,
                "香港六合彩",
                R.drawable.img_logo_hk_mark_six));
        list.add(new GameModel(Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY,
                "PC蛋蛋",
                R.drawable.img_logo_lucky_28));
        list.add(new GameModel(Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY,
                "广东快乐十分",
                R.drawable.img_logo_gd_happy_10));
        list.add(new GameModel(Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY,
                "幸运飞艇",
                R.drawable.img_logo_lucky_airship));
        list.add(new GameModel(Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY,
                "重庆幸运农场",
                R.drawable.img_logo_cq_lucky_farm));
        list.add(new GameModel(Constants.LOTTERY_TYPE.JS_QUICK_3,
                "江苏骰宝(快3)",
                R.drawable.img_logo_js_quick_3));
        list.add(new GameModel(Constants.LOTTERY_TYPE.REAL_VIDEO,
                "真人视讯",
                R.drawable.img_logo_asia_game));
        return list;
    }

    public List<MineItem> getMineItems() {
        List<MineItem> list = new ArrayList<>();
        list.add(new MineItem("个人资料", R.mipmap.icon_mine_grzl));
        list.add(new MineItem("修改密码", R.mipmap.icon_mine_xgmm));
        list.add(new MineItem("我的消息", R.mipmap.icon_mine_xxzx));
        list.add(new MineItem("资金管理", R.mipmap.icon_mine_zjgl));
        list.add(new MineItem("银行卡", R.mipmap.icon_mine_yhk));
        list.add(new MineItem("下注记录", R.mipmap.icon_mine_xzjl));
        list.add(new MineItem("新闻中心", R.mipmap.icon_mine_ggzx));
        list.add(new MineItem("关于我们", R.mipmap.icon_mine_gywm));
        list.add(new MineItem("客服中心", R.mipmap.icon_mine_kfzx));
        return list;
    }

    public List<BankRes> getBankcardSupported() {
        List<BankRes> list = new ArrayList<>();
        list.add(new BankRes("工商银行", "ICBC", R.drawable.icon_bank_logo_icbc));
        list.add(new BankRes("建设银行", "CCB", R.drawable.icon_bank_logo_ccb));
        list.add(new BankRes("招商银行", "CMB", R.drawable.icon_bank_logo_cmb));
        list.add(new BankRes("农业银行", "ABC", R.drawable.icon_bank_logo_abc));
        list.add(new BankRes("中国银行", "BOC", R.drawable.icon_bank_logo_bc));
        list.add(new BankRes("邮政储蓄银行", "PSBS", R.drawable.icon_bank_logo_psbc));
        list.add(new BankRes("民生银行", "CMBC", R.drawable.icon_bank_logo_cmbc));
        list.add(new BankRes("兴业银行", "CIB", R.drawable.icon_bank_logo_ibc));
        list.add(new BankRes("中信银行", "CTTIC", R.drawable.icon_bank_logo_cib));
        list.add(new BankRes("渤海银行", "CBHB", R.drawable.icon_bank_logo_cbhb));
        list.add(new BankRes("光大银行", "CEB", R.drawable.icon_bank_logo_ceb));
        list.add(new BankRes("广发银行", "GDB", R.drawable.icon_bank_logo_gdb));
        list.add(new BankRes("华夏银行", "HXB", R.drawable.icon_bank_logo_hxb));
        list.add(new BankRes("平安银行", "PAB", R.drawable.icon_bank_logo_pab));
        list.add(new BankRes("浦发银行", "SPDB", R.drawable.icon_bank_logo_spdb));
        list.add(new BankRes("北京农商银行", "BJRCB", R.drawable.icon_bank_logo_brcb));
        list.add(new BankRes("上海银行", "SHB", R.drawable.icon_bank_logo_bs));
        list.add(new BankRes("交通银行", "BOCO", R.drawable.icon_bank_logo_bcomm));
        return list;
    }

    public int getBankLogoResByName(String bankName) {
        List<BankRes> bankResList = getBankcardSupported();
        for (BankRes bank : bankResList) {
            if (bank.bankName.equals(bankName)) {
                return bank.logoRes;
            }
        }
        return 0;
    }

    /**
     * 根据gameCode和typeCode获取赔率
     */
    public List<MultiBetItem> getOddsData(int gameCode, String typeCode) {
        BoxStore boxStore = BaseApplication.getAppContext().getBoxStore();
        return boxStore.boxFor(MultiBetItem.class).query()
                .equal(MultiBetItem_.gameCode, gameCode)
                .equal(MultiBetItem_.typeCode, typeCode)
                .build().find();
    }

    /**
     * 根据gameCode和typeCode保存赔率到数据库
     */
    public void saveOddsData(int gameCode, String typeCode, List<MultiBetItem> data) {
        Observable.just(data)
                .observeOn(Schedulers.io())//在IO线程中执行保存操作
                .subscribe(new Consumer<List<MultiBetItem>>() {
                    @Override
                    public void accept(List<MultiBetItem> multiBetItems) throws Exception {
                        BoxStore boxStore = BaseApplication.getAppContext().getBoxStore();
                        Box<MultiBetItem> betItemBox = boxStore.boxFor(MultiBetItem.class);
                        List<MultiBetItem> oddsData = getOddsData(gameCode, typeCode);
                        betItemBox.remove(oddsData);
                        betItemBox.put(multiBetItems);
                    }
                });
    }


    /**
     * 获取连码赔率
     */
    public List<BetTypeItem> getOddsLian(int gameCode) {
        BoxStore boxStore = BaseApplication.getAppContext().getBoxStore();
        return boxStore.boxFor(BetTypeItem.class).query()
                .equal(BetTypeItem_.gameCode, gameCode)
                .build().find();
    }

    /**
     * 保存连码赔率
     */
    public void saveOddsLian(int gameCode, List<BetTypeItem> data) {
        Observable.just(data)
                .observeOn(Schedulers.io())//在IO线程中执行保存操作
                .subscribe(new Consumer<List<BetTypeItem>>() {
                    @Override
                    public void accept(List<BetTypeItem> betTypeItems) throws Exception {
                        BoxStore boxStore = BaseApplication.getAppContext().getBoxStore();
                        Box<BetTypeItem> betItemBox = boxStore.boxFor(BetTypeItem.class);
                        List<BetTypeItem> oddsData = getOddsLian(gameCode);
                        betItemBox.remove(oddsData);
                        betItemBox.put(betTypeItems);
                    }
                });
    }
}