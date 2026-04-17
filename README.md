# VungleAdsSdk for Android 🚀

[![](https://jitpack.io/v/SharifMia/VungleAds.svg)](https://jitpack.io/#SharifMia/VungleAds)

**VungleAdsSdk** is a lightweight, easy-to-use Android Java wrapper library designed to streamline the implementation of Liftoff Monetize (formerly Vungle) ads. 

It abstracts away complex setup processes, providing simple, static methods for SDK initialization, ad loading, and playback. Whether you need Interstitial or Rewarded video ads, this library offers a "plug-and-play" experience with zero boilerplate.

---

## ✨ Features
* 🚀 **Fast Initialization:** Start the Vungle SDK with a single line of code.
* 📺 **Interstitial & Rewarded Support:** Dedicated, clean manager classes for different ad formats.
* ✂️ **Zero Boilerplate:** Simple `load()` and `play()` static methods. No need to manage complex object instances.
* 🎛️ **Flexible Callbacks:** Method overloading allows you to use ads with or without verbose event listeners.
* 🛡️ **Memory Safe:** Handles contexts properly to prevent memory leaks during ad playback.

## 🤔 Why Should You Use It?
Integrating mobile ad networks usually requires writing dozens of lines of repetitive callback code, managing singletons, and checking initialization states. **VungleAdsSdk** handles all the background heavy lifting for you. 
* **Save Time:** Go from 0 to showing ads in less than 5 minutes.
* **Cleaner Codebase:** Keep your Activities and Fragments free of messy ad-logic boilerplate.
* **Beginner Friendly:** Perfect for indie developers who want to monetize their apps without spending hours reading ad network documentation.

---

## 📦 Installation

**Step 1:** Add the JitPack repository to your build file. 
Add it in your root `settings.gradle` (or project-level `build.gradle` at the end of repositories):

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } // <-- Add this line
    }
}
````

**Step 2:** Add the dependency to your app-level `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.SharifMia:VungleAds:1.0.0'
}
```

-----

## 🛠️ Step-by-Step Usage Guide

### 1\. Initialize the SDK

Initialize the SDK as early as possible (e.g., in `MainActivity.onCreate`). You only need to do this once.

**In an Activity:**

```java
import com.sharifmia.vungleadssdk.VungleAdsSdk;

String APP_ID = "YOUR_VUNGLE_APP_ID";
VungleAdsSdk.init(this, APP_ID);
```

**In a Fragment:**

```java
import com.sharifmia.vungleadssdk.VungleAdsSdk;

String APP_ID = "YOUR_VUNGLE_APP_ID";
VungleAdsSdk.init(requireContext(), APP_ID);
```

*(Optional)* You can check if the SDK is ready at any time:

```java
boolean isReady = VungleAdsSdk.isSdkInitialized();
```

-----

### 2\. Interstitial Ads (Full Screen Ads)

**A. Simple Usage (No Callbacks)**
Load the ad in the background, and play it when ready.

```java
import com.sharifmia.vungleadssdk.VungleInterstitialAds;

String PLACEMENT_ID = "YOUR_INTERSTITIAL_PLACEMENT_ID";

// 1. Load the ad
VungleInterstitialAds.load(this, PLACEMENT_ID);

// 2. Play the ad (e.g., inside a button click listener)
VungleInterstitialAds.play(this);
```

**B. Advanced Usage (With Callbacks)**
If you need to know exactly when the ad closes to move to the next screen.

```java
VungleInterstitialAds.load(this, PLACEMENT_ID, new InterstitialAdListener() {
    @Override
    public void onAdLoaded(@NonNull BaseAd baseAd) {
        // Ad is ready to show
    }

    @Override
    public void onAdEnd(@NonNull BaseAd baseAd) {
        // Ad closed. Move the user to the next screen!
    }
    
    // ... other standard Vungle callback methods
});
```

-----

### 3\. Rewarded Ads (Watch to earn)

For Rewarded Ads, you almost always want to use callbacks so you know when to reward the user.

**A. Load with Reward Listener:**

```java
import com.sharifmia.vungleadssdk.VungleRewardedAds;

String PLACEMENT_ID = "YOUR_REWARDED_PLACEMENT_ID";

VungleRewardedAds.load(this, PLACEMENT_ID, new RewardedAdListener() {
    @Override
    public void onAdLoaded(@NonNull BaseAd baseAd) {
        // Enable your "Watch Video for 50 Coins" button
    }

    @Override
    public void onAdRewarded(@NonNull BaseAd baseAd) {
        // SUCCESS! Give the user their coins/lives here.
        grantUserReward(50); 
    }

    @Override
    public void onAdEnd(@NonNull BaseAd baseAd) {
        // Ad screen closed
    }
    
    // ... other standard Vungle callback methods
});
```

**B. Play the Rewarded Ad:**

```java
// Pass the Activity or Fragment context
VungleRewardedAds.play(this); 
```

-----

## 📝 Notes on Contexts (Activity vs Fragment)

When calling `load()` or `play()`, you must pass a valid Android `Context`.

  * If you are calling these methods from inside an **Activity**, pass `this`.
  * If you are calling these methods from inside a **Fragment**, pass `requireContext()`.

## 📄 License
This project is licensed under the MIT License. This means you are free to use this SDK in any personal, commercial, or client projects without permission.
