package com.international.wtw.sports.json;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/9/8.
 */

public class HomeMsgBean implements Serializable {

    private String type_name;
    private ImageView type_img;
    private int game_code;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public ImageView getType_img() {
        return type_img;
    }

    public void setType_img(ImageView type_img) {
        this.type_img = type_img;
    }

    public int getGame_code() {
        return game_code;
    }

    public void setGame_code(int game_code) {
        this.game_code = game_code;
    }

    public HomeMsgBean() {
        super();
    }

    public HomeMsgBean(String type_name, ImageView type_img, int game_code) {
        this.type_name = type_name;
        this.type_img = type_img;
        this.game_code = game_code;
    }

    @Override
    public String toString() {
        return "HomeMsgBean{" +
                "type_name='" + type_name + '\'' +
                ", type_img=" + type_img +
                ", game_code=" + game_code +
                '}';
    }
}
