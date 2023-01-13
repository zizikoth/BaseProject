package com.memo.main.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.airbnb.lottie.LottieAnimationView
import com.memo.core.utils.ext.color
import com.memo.main.R
import com.memo.main.databinding.LottieBarBinding

/**
 * title:底部导航栏
 * describe:
 *
 * @author memo
 * @date 2022-09-27 14:36
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class LottieBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private var mBinding: LottieBarBinding

    private var lastPosition = 0
    private var action: (Int) -> Unit = {}

    init {
        mBinding = LottieBarBinding.inflate(LayoutInflater.from(context), this, true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundColor(Color.WHITE)
            this@LottieBar.outlineProvider = ViewOutlineProvider.BOUNDS
            ViewCompat.setElevation(this@LottieBar, 20f)
            clipToPadding = false
        }
        val tabs = arrayListOf<FrameLayout>()
        val lotties = arrayListOf<LottieAnimationView>()
        val dots = arrayListOf<View>()
        val texts = arrayListOf<TextView>()

        mBinding.run {
            tabs.add(mTab1)
            tabs.add(mTab2)
            tabs.add(mTab3)
            tabs.add(mTab4)

            lotties.add(mLottie1)
            lotties.add(mLottie2)
            lotties.add(mLottie3)
            lotties.add(mLottie4)

            texts.add(mTv1)
            texts.add(mTv2)
            texts.add(mTv3)
            texts.add(mTv4)

            dots.add(mDot1)
            dots.add(mDot2)
            dots.add(mDot3)
            dots.add(mDot4)

            tabs.forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (lastPosition != index) {
                        lotties[index].playAnimation()
                        texts[lastPosition].setTextColor(color(R.color.textLight))
                        texts[lastPosition].paint.typeface = Typeface.DEFAULT
                        texts[index].setTextColor(color(R.color.textDark))
                        texts[index].paint.typeface = Typeface.DEFAULT_BOLD
                        dots[lastPosition].visibility = View.GONE
                        dots[index].visibility = View.VISIBLE
                        lastPosition = index
                        action.invoke(index)
                    }
                }
            }
        }
    }

    fun setOnItemChangeListener(action: (Int) -> Unit) {
        this.action = action
    }
}