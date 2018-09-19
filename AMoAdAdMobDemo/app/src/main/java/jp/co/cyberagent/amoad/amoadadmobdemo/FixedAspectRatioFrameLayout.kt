package jp.co.cyberagent.amoad.amoadadmobdemo

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class FixedAspectRatioFrameLayout : FrameLayout {

    private var mWidthRatio: Float = 0.toFloat()
    private var mHeightRatio: Float = 0.toFloat()

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioFrameLayout)

        val aspectRatio = a.getString(R.styleable.FixedAspectRatioFrameLayout_aspect_ratio)!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mWidthRatio = java.lang.Float.parseFloat(aspectRatio[0])
        mHeightRatio = java.lang.Float.parseFloat(aspectRatio[1])

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            val height = (mHeightRatio * MeasureSpec.getSize(widthMeasureSpec) / mWidthRatio).toInt()
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            val width = (mWidthRatio * MeasureSpec.getSize(heightMeasureSpec) / mHeightRatio).toInt()
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}