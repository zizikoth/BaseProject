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

    /**
     * 设置昵称
     * @param name String 昵称
     * @return HeaderShare
     */
    fun setName(name: String): HeaderShare {
        mBinding.mTvName.text = name
        return this
    }

    /**
     * 设置积分
     * @param coin Int 积分
     * @return HeaderShare
     */
    fun setCoin(coin: Int): HeaderShare {
        mBinding.mTvCoin.text = "${coin}\n积分"
        return this
    }

    /**
     * 设置等级
     * @param level Int 等级
     * @return HeaderShare
     */
    fun setLevel(level: Int): HeaderShare {
        mBinding.mTvLevel.text = "${level}\n等级"
        return this
    }

    /**
     * 设置排名
     * @param rank Int 排名
     * @return HeaderShare
     */
    fun setRank(rank: Int): HeaderShare {
        mBinding.mTvRank.text = "${rank}\n排名"
        return this
    }
}