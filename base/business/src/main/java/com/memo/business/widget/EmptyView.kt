package com.memo.business.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.memo.business.databinding.LayoutEmptyViewBinding

/**
 * title:空视图
 * describe:
 *
 * @author memo
 * @date 2022-12-09 10:16
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private var mBinding: LayoutEmptyViewBinding

    init {
        mBinding = LayoutEmptyViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * 设置提示文字
     * @param tip String
     */
    fun setTip(tip: String): EmptyView {
        mBinding.mTvEmpty.text = tip
        return this
    }
}