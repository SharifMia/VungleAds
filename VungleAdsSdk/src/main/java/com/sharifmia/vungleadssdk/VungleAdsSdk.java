package com.sharifmia.vungleadssdk;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.vungle.ads.InitializationListener;
import com.vungle.ads.VungleAds;
import com.vungle.ads.VungleError;
public class VungleAdsSdk {

    private static final String TAG = "VungleAdsInitialize";

    // Keeps track of the initialization status
    private static boolean isInitialized = false;

    /**
     * Initializes the Vungle SDK.
     * Because this method is 'static', you can call it directly from the class name.
     */
    public static void init(Context context, String appId) {

        // Prevent redundant initialization calls
        if (isInitialized) {
            Log.d(TAG, "Vungle SDK is already initialized.");
            return;
        }

        VungleAds.init(context, appId, new InitializationListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Vungle SDK init onSuccess()");
                isInitialized = true;
            }

            @Override
            public void onError(@NonNull VungleError vungleError) {
                Log.e(TAG, "onError(): " + vungleError.getErrorMessage());
                isInitialized = false;
            }
        });
    }

    /**
     * Optional helper method if you ever need to check if Vungle is ready
     * before trying to load an ad.
     */
    public static boolean isSdkInitialized() {
        return isInitialized;
    }
}