package com.international.wtw.lottery.event;


import com.international.wtw.lottery.model.UserModel;

public class UserChangedEvent {

    public UserModel mUser;

    public UserChangedEvent(UserModel user) {
        mUser = user;
    }
}
