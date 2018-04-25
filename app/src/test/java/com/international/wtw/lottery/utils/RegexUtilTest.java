package com.international.wtw.lottery.utils;

import junit.framework.TestCase;

/**
 * Created by XiaoXin on 2017/10/3.
 * 描述：
 */
public class RegexUtilTest extends TestCase {
    public void testIsUsername() throws Exception {

    }

    public void testIsPassword() throws Exception {

    }

    public void testIsChinese() throws Exception {
        assertTrue(RegexUtil.isChinese("哈哈"));
        assertFalse(RegexUtil.isChinese("哈~"));
        assertFalse(RegexUtil.isChinese("sdfhaj"));
    }

}