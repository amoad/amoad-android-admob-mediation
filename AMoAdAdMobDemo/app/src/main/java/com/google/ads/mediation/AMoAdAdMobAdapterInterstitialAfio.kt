package com.google.ads.mediation

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.amoad.AMoAdInterstitialVideo
import com.amoad.AdResult
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener

class AMoAdAdMobAdapterInterstitialAfio : CustomEventInterstitial, AMoAdInterstitialVideo.Listener {

    private var _sid: String? = null
    private var _interstitialAfioListener: CustomEventInterstitialListener? = null
    private var _context: Context? = null

    override fun requestInterstitialAd(context: Context?, listener: CustomEventInterstitialListener?, serverParameter: String?, mediationAdRequest: MediationAdRequest?, customEventExtras: Bundle?) {

        _context = context
        _context ?: return
        _interstitialAfioListener = listener
        _interstitialAfioListener ?: return
        _sid = serverParameter
        _sid ?: return

        AMoAdInterstitialVideo.sharedInstance(_context, _sid, "").setListener(this)
        // 任意でpropertyの割り当てが可能です。
//        AMoAdInterstitialVideo.sharedInstance(_context, _sid, "").isCancellable = false
        AMoAdInterstitialVideo.sharedInstance(_context, _sid, "").load(_context)
    }

    override fun showInterstitial() {
        if (AMoAdInterstitialVideo.sharedInstance(_context, _sid, "").isLoaded) {
            AMoAdInterstitialVideo.sharedInstance(_context, _sid, "").show(_context)
        } else {
            Log.d("debug", "Interstitial Afio Ad wasn't loaded")
        }
    }

    override fun onLoad(amoadInterstitialVideo: AMoAdInterstitialVideo?, result: AdResult?) {

        when (result) {
            AdResult.Success -> {
                Log.d("debug", "広告ロード成功")
                _interstitialAfioListener?.onAdLoaded()
            }
            AdResult.Empty -> {
                Log.d("debug", "配信する広告がない")
                _interstitialAfioListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
            }
            AdResult.Failure -> {
                Log.d("debug", "広告ロード失敗")
                _interstitialAfioListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
            }
        }
    }

    override fun onStart(amoadInterstitialVideo: AMoAdInterstitialVideo?) {
        Log.d("debug", "動画の再生を開始した")
    }

    override fun onShown(amoadInterstitialVideo: AMoAdInterstitialVideo?) {
        Log.d("debug", "広告を表示した")
        _interstitialAfioListener?.onAdOpened()
    }

    override fun onComplete(amoadInterstitialVideo: AMoAdInterstitialVideo?) {
        Log.d("debug", "動画を最後まで再生完了した")
    }

    override fun onFailed(amoadInterstitialVideo: AMoAdInterstitialVideo?) {
        Log.d("debug", "動画の再生に失敗した")
    }

    override fun onDismissed(amoadInterstitialVideo: AMoAdInterstitialVideo?) {
        Log.d("debug", "広告を閉じた")
        _interstitialAfioListener?.onAdClosed()
    }

    override fun onClick(amoadInterstitialVideo: AMoAdInterstitialVideo?) {
        Log.d("debug", "広告がクリックされた")
        _interstitialAfioListener?.onAdClicked()
        _interstitialAfioListener?.onAdLeftApplication()
    }

    override fun onDestroy() {
    }

    override fun onPause() {
    }

    override fun onResume() {
    }
}