package com.international.wtw.lottery.utils;

/**
 * Created by Abin on 2017/9/11.
 * 描述：
 */

public class StringUtils {

    public static String formatBankcardId(String bankcardId) {
        if (bankcardId.isEmpty() || bankcardId == null) {
            return null;
        } else {
            return replaceAction(bankcardId, "(?<=\\d{4})\\d(?=\\d{3})");
        }
    }

    private static String replaceAction(String username, String regular) {
        return username.replaceAll(regular, "*");
    }
}
