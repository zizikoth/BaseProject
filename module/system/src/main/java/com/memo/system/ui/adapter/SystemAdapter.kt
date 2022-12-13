package com.memo.system.ui.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.memo.business.entity.remote.NodeItem
import com.memo.business.entity.remote.NodeItemChild
import com.memo.core.utils.ClickHelper
import com.memo.system.R

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-12 10:30
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */

private const val SYSTEM_TYPE_TITLE = 0
private const val SYSTEM_TYPE_ITEM = 1

class SystemAdapter : BaseNodeAdapter() {


    init {
        addFullSpanNodeProvider(SystemTitleProvider())
        addNodeProvider(SystemItemProvider())
    }

    var childClickAction: ((BaseNode) -> Unit)? = null

    override fun getItemType(data: List<BaseNode>, position: Int): Int {
        return if (data[position] is NodeItem) SYSTEM_TYPE_TITLE else SYSTEM_TYPE_ITEM
    }

    /**
     * 设置标题自动点击
     * @param position Int
     */
    fun autoClick(position: Int) {
        val data = getItem(position)
        if (data is BaseExpandNode) {
            val anim = RotateAnimation(
                if (data.isExpanded) -180f else 0f,
                if (data.isExpanded) 0f else -180f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)
            anim.duration = 300
            anim.fillAfter = true
            getViewByPosition(position, R.id.mIvOpen)?.startAnimation(anim)
            expandOrCollapse(position)
        }
    }
}


class SystemTitleProvider : BaseNodeProvider() {
    override val itemViewType: Int = SYSTEM_TYPE_TITLE
    override val layoutId: Int = R.layout.layout_item_system_title

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as NodeItem).let {
            helper.setText(R.id.mTvTitle, it.title).setGone(R.id.mLine, helper.bindingAdapterPosition == 0)
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        if (ClickHelper.isNotFastClick) {
            if (data is BaseExpandNode) {
                val anim = RotateAnimation(
                    if (data.isExpanded) -180f else 0f,
                    if (data.isExpanded) 0f else -180f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f)
                anim.duration = 300
                anim.fillAfter = true
                helper.getView<AppCompatImageView>(R.id.mIvOpen).startAnimation(anim)
            }
            getAdapter()?.expandOrCollapse(position)
        }
    }
}

class SystemItemProvider : BaseNodeProvider() {
    override val itemViewType: Int = SYSTEM_TYPE_ITEM
    override val layoutId: Int = R.layout.layout_item_system_item

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as NodeItemChild).let {
            helper.setText(R.id.mTvItem, it.title)
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        if (ClickHelper.isNotFastClick && getAdapter() is SystemAdapter) (getAdapter() as SystemAdapter).childClickAction?.invoke(data)
    }
}
