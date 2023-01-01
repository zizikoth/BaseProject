package com.memo.base.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.memo.base.databinding.LayoutCollectAnimViewBinding
import com.memo.core.utils.ext.gone
import com.memo.core.utils.ext.visible

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-16 16:01
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CollectAnimView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var collectAnimator: ValueAnimator

    private var unCollectAnimator: ValueAnimator

    private var mBinding: LayoutCollectAnimViewBinding

    init {
        mBinding = LayoutCollectAnimViewBinding.inflate(LayoutInflater.from(context), this, true)
        collectAnimator = ValueAnimator.ofFloat(0f, 1f).apply { duration = 1500 }
        collectAnimator.addUpdateListener {
            mBinding.mLottieView.progress = it.animatedValue as Float
            if ((it.animatedValue as Float) == 1f) gone()
        }

        unCollectAnimator = ValueAnimator.ofFloat(1f, 0f).apply { duration = 1500 }
        unCollectAnimator.addUpdateListener {
            mBinding.mLottieView.progress = it.animatedValue as Float
            if ((it.animatedValue as Float) == 0f) gone()
        }

        gone()
    }

    /**
     * 开启收藏动画
     */
    fun startCollect() {
        visible()
        collectAnimator.start()
    }

    /**
     * 开启取消收藏动画
     */
    fun startUnCollect() {
        visible()
        unCollectAnimator.start()
    }
}