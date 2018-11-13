package com.dtms.mvploginsample.root;

import com.dtms.mvploginsample.login.LoginActivity;
import com.dtms.mvploginsample.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);

}
