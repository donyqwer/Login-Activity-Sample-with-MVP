package com.dtms.mvploginsample.root;

import com.dtms.mvploginsample.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);
    
}
