package jp.co.cyberagent.amoad.amoadadmobdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_banner.*

class BannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        createAndLoadBannerView()
    }

    override fun onDestroy() {
        bannerView.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    internal fun createAndLoadBannerView() {

        val adRequest = AdRequest.Builder().build()
        bannerView.loadAd(adRequest)
        bannerView.adListener = object: AdListener() {

            override fun onAdLoaded() {
                Log.d("debug", "onAdLoaded")
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                Log.d("debug", "onAdFailedToLoad : $errorCode")
            }

            override fun onAdOpened() {
                Log.d("debug", "onAdOpened")
            }

            override fun onAdLeftApplication() {
                Log.d("debug", "onAdLeftApplication")
            }

            override fun onAdClosed() {
                Log.d("debug", "onAdClosed")
            }

            override fun onAdClicked() {
                Log.d("debug", "onAdClicked")
            }
        }
    }
}
