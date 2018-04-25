package com.international.wtw.sports.event;


import com.international.wtw.sports.model.UserModel;

public class UserChangedEvent {

    public UserModel mUser;

    public UserChangedEvent(UserModel user) {
        mUser = user;
    }
}
