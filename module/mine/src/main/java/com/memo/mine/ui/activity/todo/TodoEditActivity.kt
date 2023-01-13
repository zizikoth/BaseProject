package com.memo.mine.ui.activity.todo

import android.content.Context
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.local.TodoContent
import com.memo.base.entity.local.TodoPriority
import com.memo.base.entity.local.TodoType
import com.memo.base.entity.remote.TodoInfo
import com.memo.base.manager.BusManager
import com.memo.base.utils.toast
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.color
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.startActivity
import com.memo.core.utils.ext.value
import com.memo.mine.R
import com.memo.mine.databinding.ActivityTodoEditBinding
import com.memo.mine.viewmodel.TodoViewModel

/**
 * title:编辑Todo页面
 * describe:
 *
 * @author memo
 * @date 2023-01-12 11:47
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class TodoEditActivity : BaseVmActivity<TodoViewModel, ActivityTodoEditBinding>() {

    companion object {
        fun start(context: Context, info: TodoInfo) {
            context.startActivity<TodoEditActivity>("info" to info)
        }
    }

    override fun showContent(): Boolean = true

    private var form: TodoContent = TodoContent()

    private val typeList = TodoType.values().filter { it.value != -1 }
    private var typeIndex = -1
    private val priorityList = TodoPriority.values().filter { it.value != -1 }
    private var priorityIndex = -1

    /*** 初始化数据 ***/
    override fun initData() {
        intent.getParcelableExtra<TodoInfo>("info")?.run {
            form.id = id
            form.title = title
            form.content = content
            form.type = type
            form.priority = priority
        }
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mTitleBar.setTitle(if (form.id == null) "新增清单" else "修改清单")
            if (form.title.isNotEmpty()) mEtTitle.setText(form.title)
            if (form.content.isNotEmpty()) mEtContent.setText(form.content)
            typeIndex = typeList.indexOfFirst { it.value == form.type }
            if (typeIndex != -1) {
                mTvType.text = typeList[typeIndex].desc
                mTvType.setTextColor(color(R.color.textDark))
            }
            priorityIndex = priorityList.indexOfFirst { it.value == form.type }
            if (priorityIndex != -1) {
                mTvPriority.text = priorityList[priorityIndex].desc
                mTvPriority.setTextColor(color(R.color.textDark))
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            // 选择类型
            mTvType.onClick {
                DialogHelper.bottom("类型", typeList.map { it.desc }.toTypedArray(), typeIndex) {
                    typeIndex = it
                    form.type = typeList[typeIndex].value
                    mTvType.text = typeList[typeIndex].desc
                    mTvType.setTextColor(color(R.color.textDark))
                }
            }
            // 选择优先
            mTvPriority.onClick {
                DialogHelper.bottom("类型", priorityList.map { it.desc }.toTypedArray(), priorityIndex) {
                    priorityIndex = it
                    form.priority = priorityList[priorityIndex].value
                    mTvPriority.text = priorityList[priorityIndex].desc
                    mTvPriority.setTextColor(color(R.color.textDark))
                }
            }
            // 提交
            mBtnSubmit.onClick {
                form.title = mEtTitle.value
                form.content = mEtContent.value
                if (form.title.isEmpty()) {
                    toast("请输入标题")
                } else if (form.content.isEmpty()) {
                    toast("请输入内容")
                } else if (form.type == -1) {
                    toast("请选择类型")
                } else if (form.priority == -1) {
                    toast("请选择优先")
                } else {
                    if (form.id == null) {
                        // 新增
                        mViewModel.todoAdd(form)
                    } else {
                        // 修改
                        mViewModel.todoEdit(form)
                    }
                }
            }
        }

        mViewModel.addOrEditLiveData.observe(this, this::onResultResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {}

    private fun onResultResponse(info: TodoInfo) {
        BusManager.todoLiveData.post(info)
        finish()
    }
}