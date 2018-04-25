package com.international.wtw.sports.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * Created by XiaoXin on 2017/9/19.
 * 描述：
 */
@Entity
public class BetTypeItem implements MultiItemEntity {

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_COMBO = 2;

    @Id
    private Long id;

    private int itemType;
    private int gameCode;
    private int typeCode;
    private String typeName;
    private int spanSize;
    private boolean selected;
    @Convert(converter = OddsItemConverter.class, dbType = String.class)
    private OddsItem oddsItem;
    @Transient
    private String duplex;

    public BetTypeItem(int gameCode, int typeCode, String typeName, int spanSize) {
        this(gameCode, typeCode, typeName, spanSize, false);
    }

    public BetTypeItem(int gameCode, int typeCode, String typeName, int spanSize, boolean selected) {
        this.gameCode = gameCode;
        this.itemType = TYPE_NORMAL;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.spanSize = spanSize;
        this.selected = selected;
    }

    public BetTypeItem(int gameCode, int itemType, String typeName,
                       int spanSize, boolean selected, OddsItem oddsItem) {
        this.gameCode = gameCode;
        this.itemType = itemType;
        this.typeName = typeName;
        this.spanSize = spanSize;
        this.selected = selected;
        this.oddsItem = oddsItem;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public int getGameCode() {
        return this.gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OddsItem getOddsItem() {
        return oddsItem;
    }

    public void setOddsItem(OddsItem oddsItem) {
        this.oddsItem = oddsItem;
    }

    public String getDuplex() {
        return duplex;
    }

    public void setDuplex(String duplex) {
        this.duplex = duplex;
    }
}
