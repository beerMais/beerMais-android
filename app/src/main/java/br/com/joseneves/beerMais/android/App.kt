package br.com.joseneves.beerMais.android

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId


class App: Application() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}