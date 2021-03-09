package com.bignerdranch.android.simpleinstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3OlNmivNDa2BrUcgWl79QiNnLFJVVQfYnIK7BNRt")
                .clientKey("WizQmjMviVDF0E26A1YKSVec6kLDkeR1FHTi7Bjx")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
