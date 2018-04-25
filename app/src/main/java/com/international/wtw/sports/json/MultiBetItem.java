package com.international.wtw.sports.json;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


/**
 * Created by XiaoXin on 2017/9/20.
 * 描述：下注选项
 */
@Entity
public class MultiBetItem implements MultiItemEntity, Comparable<MultiBetItem> {
    public static final int TITLE = 1;
    public static final int CONTENT_TEXT = 2;
    public static final int CONTENT_NUM = 3;
    public static final int CONTENT_LIAN = 4;
    public static final int CONTENT_MULTI_NUM = 5;

    @Id
    private Long id;

    private int gameCode;

    private String typeCode;

    private int itemType;
    private int spanSize;
    private boolean isSelected;
    private String typeName;
    private int number;
    @Convert(converter = OddsItemConverter.class, dbType = String.class)
    private OddsItem betItem;

    public MultiBetItem(int gameCode, String typeCode, int itemType, int spanSize, String typeName) {
        this.gameCode = gameCode;
        this.typeCode = typeCode;
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.typeName = typeName;
    }

    public MultiBetItem(int gameCode, String typeCode, int itemType, int spanSize, String typeName, int number) {
        this.gameCode = gameCode;
        this.typeCode = typeCode;
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.typeName = typeName;
        this.number = number;
    }

    public MultiBetItem(int gameCode, String typeCode, int itemType, int spanSize, String typeName, OddsItem betItem) {
        this.gameCode = gameCode;
        this.typeCode = typeCode;
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.typeName = typeName;
        this.betItem = betItem;
    }

    public int getItemType() {
        return itemType;
    }

    @Override
    public int compareTo(@NonNull MultiBetItem o) {
        if (betItem != null) {
            /*String[] split = betItem.getKey().split("_");
            String[] split1 = o.betItem.getKey().split("_");
            if (split.length == 2 && split1.length == 2) {
                int a = Integer.valueOf(split[split.length - 1]);
                int b = Integer.valueOf(split1[split1.length - 1]);
                return a - b;
            } else if (split.length == 2 && split1.length == 3) {
                int a = Integer.valueOf(split[split.length - 1]);
                int b = Integer.valueOf(split1[split1.length - 2]);
                if (a == b) {
                    return -1;
                }
                return a - b;
            } else if (split.length == 3 && split1.length == 2) {
                int a = Integer.valueOf(split[split.length - 2]);
                int b = Integer.valueOf(split1[split1.length - 1]);
                if (a == b) {
                    return 1;
                }
                return a - b;
            } else {
                int a = Integer.valueOf(split[split.length - 2]);
                int b = Integer.valueOf(split1[split1.length - 2]);
                if (a == b) {
                    a = Integer.valueOf(split[split.length - 1]);
                    b = Integer.valueOf(split1[split1.length - 1]);
                }
                return a - b;
            }*/
            return Integer.valueOf(betItem.getKey()) - Integer.parseInt(o.betItem.getKey());
        } else {
            return this.number - o.number;
        }
    }

    @Override
    public String toString() {
        return "MultiBetItem{" +
                "id=" + id +
                ", gameCode=" + gameCode +
                ", typeCode=" + typeCode +
                ", itemType=" + itemType +
                ", spanSize=" + spanSize +
                ", isSelected=" + isSelected +
                ", typeName='" + typeName + '\'' +
                ", betItem=" + betItem +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGameCode() {
        return this.gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getSpanSize() {
        return this.spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public OddsItem getBetItem() {
        return this.betItem;
    }

    public void setBetItem(OddsItem betItem) {
        this.betItem = betItem;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
