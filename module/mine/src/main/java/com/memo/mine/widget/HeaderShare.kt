package com.memo.mine.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.blankj.utilcode.util.BarUtils
import com.memo.mine.databinding.LayoutHeaderShareBinding

/**
 * title: 个人分享列表头部
 * describe:
 *
 * @author memo
 * @date 2022-12-15 14:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@SuppressLint("SetTextI18n")
class HeaderShare @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var mBinding: LayoutHeaderShareBinding

    init {
        mBinding = LayoutHeaderShareBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setName(name: String): HeaderShare {
        mBinding.mTvName.text = name
        return this
    }

    fun setCoin(coin: Int): HeaderShare {
        mBinding.mTvCoin.text = "${coin}\n积分"
        return this
    }

    fun setLevel(level: Int): HeaderShare {
        mBinding.mTvLevel.text = "${level}\n等级"
        return this
    }

    fun setRank(rank: Int): HeaderShare {
        mBinding.mTvRank.text = "${rank}\n排名"
        return this
    }
}