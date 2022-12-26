package com.memo.business.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.memo.business.R
import com.memo.business.databinding.IconItemCellBinding
import com.memo.core.utils.ext.dp2px
import com.memo.core.utils.ext.round
import com.memo.core.utils.ext.setVisible
import com.memo.core.utils.ext.visible

/**
 * title:图标+内容+小红点(红数字)+右侧箭头
 * describe:
 *
 * @author memo
 * @date 2022-03-18 16:54
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class IconItemCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var mBinding: IconItemCellBinding

    private var drawable: Int = 0
    private var title: String = ""
    private var extra: String = ""
    private var badgeNum: Int = 0
    private var showBadge: Boolean = false
    private var showDot: Boolean = false
    private var showLine: Boolean = true

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.IconItemCell)
        drawable = attr.getResourceId(R.styleable.IconItemCell_icon_item_cell_drawable, drawable)
        title = attr.getString(R.styleable.IconItemCell_icon_item_cell_title) ?: title
        extra = attr.getString(R.styleable.IconItemCell_icon_item_cell_extra) ?: extra
        badgeNum = attr.getInt(R.styleable.IconItemCell_icon_item_cell_badge_num, badgeNum)
        showBadge = attr.getBoolean(R.styleable.IconItemCell_icon_item_cell_show_badge, showBadge)
        showDot = attr.getBoolean(R.styleable.IconItemCell_icon_item_cell_show_dot, showDot)
        showLine = attr.getBoolean(R.styleable.IconItemCell_icon_item_cell_show_line, showLine)
        attr.recycle()

        mBinding = IconItemCellBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.mTvItem.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, 0, 0, 0)
        mBinding.mTvItem.text = title

        mBinding.mTvExtra.setVisible(extra.isNotEmpty())
        mBinding.mTvExtra.text = extra

        mBinding.mTvBadge.setVisible((showBadge && badgeNum > 0) || showDot)
        if (showBadge) {
            mBinding.mTvBadge.round(16.dp2px)
            mBinding.mTvBadge.text = if (badgeNum > 99) "99+" else badgeNum.toString()
            if (badgeNum < 10) {
                mBinding.mTvBadge.layoutParams.width = 12.dp2px
                mBinding.mTvBadge.layoutParams.height = 12.dp2px
            } else {
                mBinding.mTvBadge.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                mBinding.mTvBadge.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mBinding.mTvBadge.setPadding(4.dp2px, 0, 4.dp2px, 0)
            }
        }
        if (showDot) {
            mBinding.mTvBadge.round(6.dp2px)
            mBinding.mTvBadge.text = ""
            mBinding.mTvBadge.layoutParams.width = 6.dp2px
            mBinding.mTvBadge.layoutParams.height = 6.dp2px
        }
        mBinding.mLine.setVisible(showLine)
    }

    /**
     * 设置图标
     * @param icon Int
     * @return IconItemCell
     */
    fun setIcon(@DrawableRes icon: Int): IconItemCell {
        mBinding.mTvItem.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)
        return this
    }

    /**
     * 设置标题
     * @param title String?
     * @return IconItemCell
     */
    fun setTitle(title: String?): IconItemCell {
        mBinding.mTvItem.text = title
        return this
    }

    /**
     * 设置额外文字
     * @param extra String?
     * @return IconItemCell
     */
    fun setExtra(extra: String?): IconItemCell {
        mBinding.mTvExtra.text = extra
        mBinding.mTvExtra.setVisible(!extra.isNullOrEmpty())
        return this
    }

    /**
     * 设置Badge数量
     * @param badgeNum Int
     * @return IconItemCell
     */
    fun setBadgeNum(badgeNum: Int): IconItemCell {
        mBinding.mTvBadge.visible()
        mBinding.mTvBadge.text = if (badgeNum > 99) "99+" else badgeNum.toString()
        if (badgeNum < 10) {
            mBinding.mTvBadge.layoutParams.width = 16.dp2px
            mBinding.mTvBadge.layoutParams.height = 16.dp2px
        } else {
            mBinding.mTvBadge.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            mBinding.mTvBadge.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            mBinding.mTvBadge.setPadding(4.dp2px, 0, 4.dp2px, 0)
        }
        return this
    }

    /**
     * 是否显示Badge
     * @param show Boolean
     * @return IconItemCell
     */
    fun showBadge(show: Boolean): IconItemCell {
        mBinding.mTvBadge.setVisible(show)
        return this
    }

    /**
     * 是否显示Dot
     * @param show Boolean
     * @return IconItemCell
     */
    fun showDot(show: Boolean): IconItemCell {
        mBinding.mTvBadge.setVisible(show)
        if (show) {
            mBinding.mTvBadge.text = ""
            mBinding.mTvBadge.layoutParams.width = 6.dp2px
            mBinding.mTvBadge.layoutParams.height = 6.dp2px
        }
        return this
    }
}