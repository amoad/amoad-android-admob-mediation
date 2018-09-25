package jp.co.cyberagent.amoad.amoadadmobdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_interstitial_afio.*

class InterstitialAfioActivity : AppCompatActivity() {

    private var adUnitID = "管理画面から取得したAdUnitIDを指定してください。"
    private lateinit var interstitialAfio: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_afio)
        showBtn.setOnClickListener{ show() }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun show() {
        createAndLoadInterstitialAfio()
    }

    internal fun createAndLoadInterstitialAfio() {

        interstitialAfio = InterstitialAd(this)
        interstitialAfio.adUnitId = adUnitID
        interstitialAfio.loadAd(AdRequest.Builder().build())
        interstitialAfio.adListener = object: AdListener() {

            override fun onAdLoaded() {
                Log.d("debug", "onAdLoaded")
                if (interstitialAfio.isLoaded) {
                    interstitialAfio.show()
                } else {
                    Log.d("debug", "The interstitialAfio wasn't loaded yet.")
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

            override fun onAdClicked() {
                Log.d("debug", "onAdClicked")
            }
        }
    }
}
