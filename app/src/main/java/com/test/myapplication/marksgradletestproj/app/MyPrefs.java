package com.test.myapplication.marksgradletestproj.app;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by mark on 5/27/14.
 */
@SharedPref
public interface MyPrefs {
    @DefaultBoolean(false)
    boolean rememberMe();
}
