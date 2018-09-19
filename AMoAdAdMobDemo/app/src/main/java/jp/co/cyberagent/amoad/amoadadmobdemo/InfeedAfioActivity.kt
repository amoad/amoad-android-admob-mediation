package jp.co.cyberagent.amoad.amoadadmobdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.ads.mediation.AMoAdAdMobAdapterInfeedAfio
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_infeed_afio.*

class InfeedAfioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infeed_afio)
        createAndLoadInfeedAfio()
    }

    override fun onDestroy() {
        infeedAfio.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        Log.d("debug", "onBackPressed")
        super.onBackPressed()
    }

    internal fun createAndLoadInfeedAfio() {

        val extras = Bundle()
        extras.putString("adView", "item_afio")

        val request = AdRequest.Builder().addCustomEventExtrasBundle(AMoAdAdMobAdapterInfeedAfio::class.java, extras).build()
        infeedAfio.loadAd(request)

        infeedAfio.adListener = object: AdListener() {

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
        }
    }
}
