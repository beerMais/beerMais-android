package br.com.joseneves.beerMais.android

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics


class App: Application() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}