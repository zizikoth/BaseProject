package com.memo.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.memo.core.R
import com.memo.core.databinding.ItemCellBinding
import com.memo.core.utils.extra.dp2px

/**
 * title:条目空间
 * describe:左侧标题 右侧内容
 *
 * @author memo
 * @date 2022-03-18 16:31
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ItemCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mBinding: ItemCellBinding

    private var title: String = ""
    private var content: String = ""
    private var titleWidth: Int = 150.dp2px

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ItemCell)
        title = attr.getString(R.styleable.ItemCell_item_cell_title) ?: title
        content = attr.getString(R.styleable.ItemCell_item_cell_content) ?: content
        titleWidth = attr.getDimension(R.styleable.ItemCell_item_cell_width, titleWidth.toFloat()).toInt()
        attr.recycle()

        mBinding = ItemCellBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.mTvTitle.text = title
        mBinding.mTvTitle.layoutParams.width = titleWidth
        mBinding.mTvContent.text = content
    }

    /**
     * 设置标题
     * @param title String?
     * @return ItemCell
     */
    fun setTitle(title: String?): ItemCell {
        title?.let { mBinding.mTvTitle.text = it }
        return this
    }

    /**
     * 设置内容
     * @param content String?
     * @return ItemCell
     */
    fun setContent(content: String?): ItemCell {
        content?.let { mBinding.mTvContent.text = it }
        return this
    }

    /**
     * 设置标题宽度
     * @param width Int
     * @return ItemCell
     */
    fun setTitleWidth(width: Int): ItemCell {
        mBinding.mTvTitle.layoutParams.width = width
        return this
    }
}