package com.sharifmia.vungleadssdk;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vungle.ads.AdConfig;
import com.vungle.ads.BaseAd;
import com.vungle.ads.RewardedAd;
import com.vungle.ads.RewardedAdListener;
import com.vungle.ads.VungleError;

public class VungleRewardedAds {

    private static final String TAG = "VungleRewardedAds";

    // Hold a static reference to the rewarded ad instance
    private static RewardedAd rewardedAd;

    /**
     * Basic Load Method (No Callbacks)
     * Use this if you just want to load the ad in the background.
     */
    public static void load(Context context, String placementId) {
        // Call the overloaded method with a null listener
        load(context, placementId, null);
    }

    /**
     * Advanced Load Method (With Callbacks)
     * Use this to know exactly when the ad loads, and crucially, when the user earns the reward.
     */
    public static void load(Context context, String placementId, @Nullable RewardedAdListener userListener) {

        // Initialize the Rewarded Ad object
        rewardedAd = new RewardedAd(context, placementId, new AdConfig());

        // Set up the listener to handle ad events internally and pass them to the user if provided
        rewardedAd.setAdListener(new RewardedAdListener() {
            @Override
            public void onAdLoaded(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Rewarded Ad Loaded successfully.");
                if (userListener != null) userListener.onAdLoaded(baseAd);
            }

            @Override
            public void onAdStart(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Rewarded Ad Started.");
                if (userListener != null) userListener.onAdStart(baseAd);
            }

            @Override
            public void onAdEnd(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Rewarded Ad Ended/Closed.");
                if (userListener != null) userListener.onAdEnd(baseAd);
            }

            @Override
            public void onAdClicked(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Rewarded Ad Clicked.");
                if (userListener != null) userListener.onAdClicked(baseAd);
            }

            @Override
            public void onAdLeftApplication(@NonNull BaseAd baseAd) {
                Log.d(TAG, "User left the application from the ad.");
                if (userListener != null) userListener.onAdLeftApplication(baseAd);
            }

            @Override
            public void onAdFailedToLoad(@NonNull BaseAd baseAd, @NonNull VungleError vungleError) {
                Log.e(TAG, "Failed to load Rewarded Ad: " + vungleError.getErrorMessage());
                if (userListener != null) userListener.onAdFailedToLoad(baseAd, vungleError);
            }

            @Override
            public void onAdFailedToPlay(@NonNull BaseAd baseAd, @NonNull VungleError vungleError) {
                Log.e(TAG, "Failed to play Rewarded Ad: " + vungleError.getErrorMessage());
                if (userListener != null) userListener.onAdFailedToPlay(baseAd, vungleError);
            }

            @Override
            public void onAdImpression(@NonNull BaseAd baseAd) {
                Log.d(TAG, "Rewarded Ad Impression Logged.");
                if (userListener != null) userListener.onAdImpression(baseAd);
            }

            // --- THIS IS UNIQUE TO REWARDED ADS ---
            @Override
            public void onAdRewarded(@NonNull BaseAd baseAd) {
                Log.d(TAG, "User watched the video and earned the reward!");
                if (userListener != null) userListener.onAdRewarded(baseAd);
            }
        });

        // Trigger the network request to load the ad
        rewardedAd.load();
    }

    /**
     * Play the Ad
     * Call this when you are ready to show the loaded ad.
     * @param context The current Activity or Fragment context.
     */
    public static void play(Context context) {
        if (rewardedAd != null && rewardedAd.canPlayAd()) {
            rewardedAd.play(context);
        } else {
            Log.w(TAG, "Rewarded Ad is not ready to play yet. Make sure it has loaded first.");
        }
    }
}
