package com.memo.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.memo.base.R
import com.memo.base.databinding.LayoutEmptyViewBinding

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

    companion object {
        const val EMPTY_DATA = 1L
        const val EMPTY_SEARCH = 2L
        const val EMPTY_NOTIFY = 3L
        const val EMPTY_COLLECT = 4L
        const val EMPTY_TODO = 5L
    }

    private var mBinding: LayoutEmptyViewBinding

    init {
        mBinding = LayoutEmptyViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setType(@androidx.annotation.IntRange(from = EMPTY_DATA, to = EMPTY_TODO) type: Long): EmptyView {
        var message = ""
        var cover = 0
        when (type) {
            EMPTY_SEARCH -> {
                message = "暂无搜索内容"
                cover = R.mipmap.icon_status_empty_search
            }
            EMPTY_NOTIFY -> {
                message = "暂无通知"
                cover = R.mipmap.icon_status_empty_notify
            }
            EMPTY_COLLECT -> {
                message = "暂无收藏数据"
                cover = R.mipmap.icon_status_empty_collect
            }
            EMPTY_TODO -> {
                message = "暂无待办清单"
                cover = R.mipmap.icon_status_empty_todo
            }
            else -> {
                message = "暂无数据"
                cover = R.mipmap.icon_status_empty_data
            }
        }
        mBinding.mIvCover.setImageResource(cover)
        mBinding.mTvEmpty.text = message
        return this
    }
}