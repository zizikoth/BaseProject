package com.memo.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.memo.core.R
import com.memo.core.databinding.IconItemCellBinding
import com.memo.core.utils.extra.dp2px
import com.memo.core.utils.extra.setVisible
import com.memo.core.utils.extra.visible

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
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mBinding: IconItemCellBinding

    private var icon: Int = 0
    private var title: String = ""
    private var badgeNum: Int = 0
    private var showBadge: Boolean = false
    private var showDot: Boolean = false

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.IconItemCell)
        icon = attr.getResourceId(R.styleable.IconItemCell_icon_item_cell_drawable, icon)
        title = attr.getString(R.styleable.IconItemCell_icon_item_cell_title) ?: title
        badgeNum = attr.getInt(R.styleable.IconItemCell_icon_item_cell_badge_num, badgeNum)
        showBadge = attr.getBoolean(R.styleable.IconItemCell_icon_item_cell_show_badge, showBadge)
        showDot = attr.getBoolean(R.styleable.IconItemCell_icon_item_cell_show_dot, showDot)
        attr.recycle()

        mBinding = IconItemCellBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.mTvItem.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)
        mBinding.mTvItem.text = title
        mBinding.mBadge.setVisible((showBadge && badgeNum > 0) || showDot)
        if (showBadge) {
            mBinding.mBadge.text = if (badgeNum > 99) "99+" else badgeNum.toString()
            if (badgeNum < 10) {
                mBinding.mBadge.layoutParams.width = 16.dp2px
                mBinding.mBadge.layoutParams.height = 16.dp2px
            } else {
                mBinding.mBadge.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                mBinding.mBadge.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mBinding.mBadge.setPadding(4.dp2px, 0, 4.dp2px, 0)
            }
        }
        if (showDot) {
            mBinding.mBadge.text = ""
            mBinding.mBadge.layoutParams.width = 6.dp2px
            mBinding.mBadge.layoutParams.height = 6.dp2px
        }
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
        title?.let { mBinding.mTvItem.text = it }
        return this
    }

    /**
     * 设置Badge数量
     * @param badgeNum Int
     * @return IconItemCell
     */
    fun setBadgeNum(badgeNum: Int): IconItemCell {
        mBinding.mBadge.visible()
        mBinding.mBadge.text = if (badgeNum > 99) "99+" else badgeNum.toString()
        if (badgeNum < 10) {
            mBinding.mBadge.layoutParams.width = 16.dp2px
            mBinding.mBadge.layoutParams.height = 16.dp2px
        } else {
            mBinding.mBadge.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            mBinding.mBadge.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            mBinding.mBadge.setPadding(4.dp2px, 0, 4.dp2px, 0)
        }
        return this
    }

    /**
     * 是否显示Badge
     * @param show Boolean
     * @return IconItemCell
     */
    fun showBadge(show: Boolean): IconItemCell {
        mBinding.mBadge.setVisible(show)
        return this
    }

    /**
     * 是否显示Dot
     * @param show Boolean
     * @return IconItemCell
     */
    fun showDot(show: Boolean): IconItemCell {
        mBinding.mBadge.setVisible(show)
        if (show) {
            mBinding.mBadge.text = ""
            mBinding.mBadge.layoutParams.width = 6.dp2px
            mBinding.mBadge.layoutParams.height = 6.dp2px
        }
        return this
    }
}