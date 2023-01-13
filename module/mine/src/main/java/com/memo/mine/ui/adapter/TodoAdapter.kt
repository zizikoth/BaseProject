package com.memo.mine.ui.adapter

import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.daimajia.swipe.SwipeLayout
import com.memo.base.entity.local.TodoStatus
import com.memo.base.entity.local.TodoType
import com.memo.base.entity.remote.TodoInfo
import com.memo.base.widget.LabelView
import com.memo.core.utils.ext.circle
import com.memo.core.utils.ext.color
import com.memo.core.utils.ext.dp2px
import com.memo.core.utils.ext.round
import com.memo.mine.R

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2023-01-01 23:00
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class TodoAdapter : BaseQuickAdapter<TodoInfo, BaseViewHolder>(R.layout.layout_item_todo) {

    private val radius = 8.dp2px

    init {
        addChildClickViewIds(R.id.mLlContent, R.id.mIvStatus, R.id.mIvEdit, R.id.mIvDelete)
    }

    override fun convert(holder: BaseViewHolder, item: TodoInfo) {
        holder.run {
            getView<SwipeLayout>(R.id.mSwipeLayout).close()
            val color = when (item.type) {
                TodoType.WORK.value -> color(R.color.color_todo_work)
                TodoType.LIFE.value -> color(R.color.color_todo_life)
                TodoType.MINE.value -> color(R.color.color_todo_mine)
                TodoType.OTHER.value -> color(R.color.color_todo_other)
                else -> color(R.color.color_todo_other)
            }
            getView<LinearLayout>(R.id.mLlContent).round(color, radius)
            getView<LabelView>(R.id.mLabelView).text = item.getTypeDesc()
            val complete = item.status == TodoStatus.COMPLETE.value
            setImageResource(
                R.id.mIvStatus, if (complete) R.drawable.icon_todo_uncomplete else R.drawable.icon_todo_complete)

            setText(R.id.mTvTitle, item.title)
            setText(R.id.mTvContent, item.content)
            setText(
                R.id.mTvTimePriority,
                (if (complete) "完成时间：${item.completeDateStr}" else "创建时间：${item.dateStr}") + "\t\t[ " + item.getPriorityDesc() + " ]")
            setVisible(R.id.mIvComplete, complete)
            setGone(R.id.mIvEdit,complete)
        }
    }

    fun close(position: Int) {
        (getViewByPosition(position, R.id.mSwipeLayout) as SwipeLayout?)?.close()
    }
}