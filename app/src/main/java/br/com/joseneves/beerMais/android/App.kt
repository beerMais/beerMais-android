package br.com.joseneves.beerMais.android

import android.app.Application
import com.google.android.gms.ads.MobileAds


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
    }
}