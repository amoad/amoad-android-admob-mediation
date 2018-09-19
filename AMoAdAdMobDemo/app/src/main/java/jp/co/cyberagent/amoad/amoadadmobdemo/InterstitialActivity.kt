package jp.co.cyberagent.amoad.amoadadmobdemo

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_interstitial.*

class InterstitialActivity : AppCompatActivity() {

    private var adUnitID = "ca-app-pub-6717685917384193/9752573348"
    private lateinit var interstitial: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial)
        showBtn.setOnClickListener{ show() }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun show() {
        createAndLoadInterstitial()
    }

    internal fun createAndLoadInterstitial() {

        interstitial = InterstitialAd(this)
        interstitial.adUnitId = adUnitID
        interstitial.loadAd(AdRequest.Builder().build())
        interstitial.adListener = object: AdListener() {

            override fun onAdLoaded() {
                Log.d("debug", "onAdLoaded")
                if (interstitial.isLoaded) {
                    interstitial.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
            }

            override fun onAdFailedToLoad(errorCode: Int) {
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
