package com.dtms.mvploginsample.login;

public interface LoginRepository {

    User getUser();

    void saveUser(User user);
}
