package com.international.wtw.lottery.json;

import com.international.wtw.lottery.utils.JsonUtil;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by XiaoXin on 2017/10/10.
 * 描述：
 */

public class OddsItemConverter implements PropertyConverter<OddsItem, String> {

    @Override
    public OddsItem convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return JsonUtil.fromJson(databaseValue, OddsItem.class);
    }

    @Override
    public String convertToDatabaseValue(OddsItem entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        return JsonUtil.toJson(entityProperty);
    }
}
