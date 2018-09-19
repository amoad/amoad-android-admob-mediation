package com.google.ads.mediation

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.amoad.AMoAdView
import com.amoad.AdCallback
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener

class AMoAdAdMobAdapterBanner : CustomEventBanner, AdCallback {

    private var _amoadView: AMoAdView? = null
    private var _bannerListener: CustomEventBannerListener? = null

    override fun requestBannerAd(context: Context, listener: CustomEventBannerListener, serverParameter: String, size: AdSize, mediationAdRequest: MediationAdRequest, customEventExtras: Bundle?) {

        _bannerListener = listener
        _bannerListener ?: return

        if(_amoadView == null) {
            _amoadView = AMoAdView(context)
            _amoadView?.sid = serverParameter
            _amoadView?.setCallback(this)
        }
    }

    override fun didReceiveAd() {
        Log.d("debug", "受信成功")
        _bannerListener?.onAdLoaded(_amoadView)
    }

    override fun didFailToReceiveAdWithError() {
        Log.d("debug", "受信失敗")
        _bannerListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
    }

    override fun didReceiveEmptyAd() {
        Log.d("debug", "広告が配信されてない")
        _bannerListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
    }

    override fun onDestroy() {
    }

    override fun onPause() {
    }

    override fun onResume() {
    }
}