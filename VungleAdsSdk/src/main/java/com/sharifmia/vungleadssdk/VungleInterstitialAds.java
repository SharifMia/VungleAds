package com.sharifmia.vungleadssdk;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vungle.ads.AdConfig;
import com.vungle.ads.BaseAd;
import com.vungle.ads.InterstitialAd;
import com.vungle.ads.InterstitialAdListener;
import com.vungle.ads.VungleError;

public class VungleInterstitialAds {

    private static final String TAG = "VungleInterstitialAds";

    // Hold a static reference to the ad instance
    private static InterstitialAd interstitialAd;

    /**
     * Basic Load Method (No Callbacks)
     * Use this if you just want to load the ad in the background and don't need to listen for events.
     */
    public static void load(Context context, String placementId) {
        // Call the overloaded method with a null listener
        load(context, placementId, null);
    }

    /**
     * Advanced Load Method (With Callbacks)
     * Use this if you want to know exactly when the ad loads, fails, closes, etc.
     */
    public static void load(Context context, String placementId, @Nullable InterstitialAdListener userListener) {

        // Initialize the Interstitial Ad object
        interstitialAd = new InterstitialAd(context, placementId, new AdConfig());

        // Set up the listener to handle ad events internally and pass them to the user if provided
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onAdLoaded(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Interstitial Ad Loaded successfully.");
                if (userListener != null) userListener.onAdLoaded(baseAd);
            }

            @Override
            public void onAdStart(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Interstitial Ad Started.");
                if (userListener != null) userListener.onAdStart(baseAd);
            }

            @Override
            public void onAdEnd(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Interstitial Ad Ended/Closed.");
                if (userListener != null) userListener.onAdEnd(baseAd);
            }

            @Override
            public void onAdClicked(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Interstitial Ad Clicked.");
                if (userListener != null) userListener.onAdClicked(baseAd);
            }

            @Override
            public void onAdLeftApplication(@NonNull BaseAd baseAd) {
                Log.d(TAG, "User left the application from the ad.");
                if (userListener != null) userListener.onAdLeftApplication(baseAd);
            }

            @Override
            public void onAdFailedToLoad(@NonNull BaseAd baseAd, @NonNull VungleError vungleError) {
                Log.e(TAG, "Failed to load Interstitial Ad: " + vungleError.getErrorMessage());
                if (userListener != null) userListener.onAdFailedToLoad(baseAd, vungleError);
            }

            @Override
            public void onAdFailedToPlay(@NonNull BaseAd baseAd, @NonNull VungleError vungleError) {
                Log.e(TAG, "Failed to play Interstitial Ad: " + vungleError.getErrorMessage());
                if (userListener != null) userListener.onAdFailedToPlay(baseAd, vungleError);
            }

            @Override
            public void onAdImpression(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Interstitial Ad Impression Logged.");
                if (userListener != null) userListener.onAdImpression(baseAd);
            }
        });

        // Trigger the network request to load the ad
        interstitialAd.load();
    }


    /**
     * Play the Ad
     * Call this when you are ready to show the loaded ad.
     * @param context The current Activity or Fragment context.
     */
    public static void play(Context context) {
        if (interstitialAd != null && interstitialAd.canPlayAd()) {
            // Pass the context here!
            interstitialAd.play(context);
        } else {
            Log.w(TAG, "Ad is not ready to play yet. Make sure it has loaded first.");
        }
    }

}
