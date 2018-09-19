package com.google.ads.mediation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.amoad.AMoAdNativeListener
import com.amoad.AMoAdNativeViewManager
import com.amoad.AdResult
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.mediation.MediationAdRequest
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener

class AMoAdAdMobAdapterInfeedAfio : CustomEventBanner, AMoAdNativeListener {

    private var _infeedAfioListener: CustomEventBannerListener? = null
    var view: View? = null

    override fun requestBannerAd(context: Context, listener: CustomEventBannerListener, serverParameter: String, size: AdSize, mediationAdRequest: MediationAdRequest, customEventExtras: Bundle?) {

        _infeedAfioListener = listener
        _infeedAfioListener ?: return
        var extras = customEventExtras
        extras ?: return
        var sid = serverParameter
        sid ?: return

        val resId = getResourceId(extras.getString("adView"), "layout", context)
        view = LayoutInflater.from(context).inflate(resId, LinearLayout(context) as ViewGroup)
        view?.visibility = View.INVISIBLE

        // 広告準備・取得
        AMoAdNativeViewManager.getInstance(context).prepareAd(sid, true, true)
        AMoAdNativeViewManager.getInstance(context).renderAd(sid, "", view, this)
    }

    internal fun getResourceId(resourceName: String, type: String, context: Context): Int {
        return context.resources.getIdentifier(resourceName, "layout", context.packageName)
    }

    override fun onReceived(s: String, s1: String, view: View, result: AMoAdNativeListener.Result) {
        when (result) {
            AdResult.Success -> {
                Log.d("debug", "広告ロード成功")
                _infeedAfioListener?.onAdLoaded(view)
            }
            AdResult.Empty -> {
                Log.d("debug", "配信する広告がない")
                _infeedAfioListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
            }
            AdResult.Failure -> {
                Log.d("debug", "広告ロード失敗")
                _infeedAfioListener?.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR)
            }
        }
    }

    override fun onIconReceived(s: String, s1: String, view: View, result: AMoAdNativeListener.Result) {
        when (result) {
            AdResult.Success -> {
                Log.d("debug", "アイコン取得成功")
            }
            AdResult.Empty -> {
                Log.d("debug", "配信するアイコンがない")
            }
            AdResult.Failure -> {
                Log.d("debug", "アイコン取得失敗")
            }
        }
    }

    override fun onImageReceived(s: String, s1: String, view: View, result: AMoAdNativeListener.Result) {
        when (result) {
            AdResult.Success -> {
                Log.d("debug", "メイン画像取得成功")
                view?.visibility = View.VISIBLE
            }
            AdResult.Empty -> {
                Log.d("debug", "配信するメイン画像がない")

            }
            AdResult.Failure -> {
                Log.d("debug", "メイン画像取得失敗")
            }
        }
    }

    override fun onClicked(s: String, s1: String, view: View) {
        Log.d("debug", "広告クリック")
        _infeedAfioListener?.onAdClicked()
        _infeedAfioListener?.onAdLeftApplication()
    }

    override fun onDestroy() {
    }

    override fun onPause() {
    }

    override fun onResume() {
    }
}