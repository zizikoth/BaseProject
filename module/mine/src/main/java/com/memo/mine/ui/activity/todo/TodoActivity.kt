package com.memo.mine.ui.activity.todo

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.local.TodoFilter
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.TodoInfo
import com.memo.base.manager.RouteManager
import com.memo.base.utils.finish
import com.memo.base.utils.showEmpty
import com.memo.base.utils.toast
import com.memo.core.utils.ext.onClick
import com.memo.mine.databinding.ActivityTodoBinding
import com.memo.mine.ui.adapter.TodoAdapter
import com.memo.mine.viewmodel.TodoViewModel


class TodoActivity : BaseVmActivity<TodoViewModel, ActivityTodoBinding>() {

    private val filter = TodoFilter()
    private val mAdapter = TodoAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mRvList.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mRefreshLayout.setOnRefreshListener {
                filter.pageNum = 1
                mViewModel.todoList(filter)
            }
            mRefreshLayout.setOnLoadMoreListener {
                mViewModel.todoList(filter)
            }

            mBtnAdd.onClick {  }
        }

        mViewModel.listLiveData.observe(this, this::onListResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.todoList(filter)
    }

    /**
     * 获取Todo列表返回
     * @param data ListEntity<TodoInfo>
     */
    private fun onListResponse(data: ListEntity<TodoInfo>) {
        mAdapter.showEmpty(data.isEmpty())
        if(data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        filter.pageNum = data.curPage + 1
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

}