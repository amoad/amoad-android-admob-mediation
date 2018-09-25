package com.google.ads.mediation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.amoad.AdResult
import com.amoad.InterstitialAd
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener

class AMoAdAdMobAdapterInterstitial : CustomEventInterstitial {

    private var _sid: String? = null
    private var _interstitialListener: CustomEventInterstitialListener? = null
    private var _context: Context? = null

    companion object {
        private var sid: String? = null
        private fun saveSid(sid: String?) {
            AMoAdAdMobAdapterInterstitial.sid = sid
        }
        private fun deleteSid() {
            AMoAdAdMobAdapterInterstitial.sid = null
        }
        fun readSid() : String? {
            return AMoAdAdMobAdapterInterstitial.sid
        }
        fun closeInterstitial(sid: String?) {
            InterstitialAd.close(sid)
            AMoAdAdMobAdapterInterstitial.deleteSid()
        }
    }

    override fun requestInterstitialAd(context: Context?, listener: CustomEventInterstitialListener?, serverParameter: String?, mediationAdRequest: MediationAdRequest?, customEventExtras: Bundle?) {

        _context = context
        _context ?: return
        _interstitialListener = listener
        _interstitialListener ?: return
        _sid = serverParameter
        _sid ?: return

        InterstitialAd.register(_sid)
//        InterstitialAd.setAutoReload(_sid, true) // 任意でpropertyの割り当てが可能です。
        InterstitialAd.load(context, _sid) { sid, result, error ->
            when (result) {
                AdResult.Success -> {
                    Log.d("debug", "広告ロード成功")
                    _interstitialListener?.onAdLoaded()
                }
                AdResult.Empty -> {
                    Log.d("debug", "配信する広告がない")
                    _interstitialListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
                }
                AdResult.Failure -> {
                    Log.d("debug", "広告ロード失敗")
                    _interstitialListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
                }
            }
        }
    }

    override fun showInterstitial() {

        if (InterstitialAd.isLoaded(_sid)) {

            _interstitialListener?.onAdOpened()

            // close用にstaticでsidを保持
            _sid.let { AMoAdAdMobAdapterInterstitial.saveSid(it) }

            InterstitialAd.show(_context as Activity?, _sid) { result ->

                when (result) {
                    InterstitialAd.Result.Click -> {
                        Log.d("debug", "リンクボタンがクリックされたので閉じました")
                        _interstitialListener?.onAdClicked()
                        _interstitialListener?.onAdLeftApplication()
                        _interstitialListener?.onAdClosed()
                    }
                    InterstitialAd.Result.Failure -> {
                        Log.d("debug", "広告の取得に失敗しました")
                        _interstitialListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
                    }
                    InterstitialAd.Result.Duplicated -> {
                        Log.d("debug", "既に開かれているので開きませんでした")
                    }
                    InterstitialAd.Result.CloseFromApp -> {
                        Log.d("debug", "アプリから閉じられました")
                        _interstitialListener?.onAdClosed()
                    }
                    InterstitialAd.Result.Close -> {
                        Log.d("debug", "閉じるボタンがクリックされたので閉じました")
                        _interstitialListener?.onAdClosed()
                    }
                }
            }
        } else {
            Log.d("debug", "Interstitial Ad wasn't loaded")
        }
    }

    override fun onDestroy() {
    }

    override fun onPause() {
    }

    override fun onResume() {
    }
}
