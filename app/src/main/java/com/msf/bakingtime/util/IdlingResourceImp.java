package com.msf.bakingtime.util;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class IdlingResourceImp implements IdlingResource {

    private AtomicBoolean mAtomicBoolean = new AtomicBoolean(true);
    private ResourceCallback mResourceCallBack;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mAtomicBoolean.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallBack = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        mAtomicBoolean.set(isIdleNow);
        if (isIdleNow && mResourceCallBack != null) {
            mResourceCallBack.onTransitionToIdle();
        }
    }
}
