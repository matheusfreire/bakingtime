package com.msf.bakingtime.util;

import android.os.Handler;
import android.support.annotation.Nullable;

public class Delayer {

    private static final int DELAY_MILLIS = 3000;

    public interface DelayerCallback {
        void onDone(boolean finalized);
    }

    public static void processMessage(final boolean finalized, final DelayerCallback callback,
                               @Nullable final IdlingResourceImp idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(finalized);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }

}
