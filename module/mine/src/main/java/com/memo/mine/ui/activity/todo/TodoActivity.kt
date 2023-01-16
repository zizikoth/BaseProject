package com.memo.mine.ui.activity.todo

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.TimeUtils
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.local.*
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.TodoInfo
import com.memo.base.manager.BusManager
import com.memo.base.utils.finish
import com.memo.base.utils.onItemChildClick
import com.memo.base.utils.showEmpty
import com.memo.base.widget.EmptyView
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.startActivity
import com.memo.mine.R
import com.memo.mine.databinding.ActivityTodoBinding
import com.memo.mine.ui.adapter.TodoAdapter
import com.memo.mine.viewmodel.TodoViewModel

/**
 * title:待办清单列表
 * describe:
 *
 * @author memo
 * @date 2023-01-13 09:56
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class TodoActivity : BaseVmActivity<TodoViewModel, ActivityTodoBinding>() {

    /*** 筛选 ***/
    private val filter = TodoFilter()
    private val mAdapter = TodoAdapter()

    /*** 类型 ***/
    private val typeList = TodoType.values()
    private var typeIndex = 0

    /*** 状态 ***/
    private val statusList = TodoStatus.values()
    private var statusIndex = 0

    /*** 优先级 ***/
    private val priorityList = TodoPriority.values()
    private var priorityIndex = 0

    /*** 排序 ***/
    private val orderByList = TodoOrderBy.values()
    private var orderByIndex = 0

    /*** 初始化数据 ***/
    override fun initData() {}

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.mRvList.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            // 类型
            mTvType.onClick {
                DialogHelper.bottom("类型", typeList.map { it.desc }.toTypedArray(), typeIndex) {
                    typeIndex = it
                    val type = typeList[it]
                    mTvType.text = if (type.desc == "全部") "类型" else type.desc
                    filter.pageNum = 1
                    filter.type = type
                    mViewModel.todoList(filter)
                }
            }
            // 状态
            mTvStatus.onClick {
                DialogHelper.bottom("状态", statusList.map { it.desc }.toTypedArray(), statusIndex) {
                    statusIndex = it
                    val status = statusList[it]
                    mTvType.text = if (status.desc == "全部") "状态" else status.desc
                    filter.pageNum = 1
                    filter.status = status
                    showLoading()
                    mViewModel.todoList(filter)
                }
            }
            // 优先
            mTvPriority.onClick {
                DialogHelper.bottom("优先", priorityList.map { it.desc }.toTypedArray(), priorityIndex) {
                    priorityIndex = it
                    val priority = priorityList[it]
                    mTvType.text = if (priority.desc == "全部") "优先" else priority.desc
                    filter.pageNum = 1
                    filter.priority = priority
                    showLoading()
                    mViewModel.todoList(filter)
                }
            }
            // 排序
            mTvOrderBy.onClick {
                DialogHelper.bottom("排序", orderByList.map { it.desc }.toTypedArray(), orderByIndex) {
                    orderByIndex = it
                    mTvOrderBy.text = orderByList[it].desc
                    filter.pageNum = 1
                    filter.orderBy = orderByList[it]
                    showLoading()
                    mViewModel.todoList(filter)
                }
            }
            // 刷新
            mRefreshLayout.setOnRefreshListener {
                filter.pageNum = 1
                mViewModel.todoList(filter)
            }
            // 加载
            mRefreshLayout.setOnLoadMoreListener {
                mViewModel.todoList(filter)
            }
            // 新增
            mBtnAdd.onClick {
                mContext.startActivity<TodoEditActivity>()
            }

            mAdapter.onItemChildClick { viewId, data ->
                mAdapter.close(mAdapter.getItemPosition(data))
                when (viewId) {
                    // 修改完成未完成状态
                    R.id.mIvStatus -> {
                        val complete = data.status == TodoStatus.UN_COMPLETE.value
                        mViewModel.todoStatus(data.id, if (complete) TodoStatus.COMPLETE else TodoStatus.UN_COMPLETE)
                    }
                    // 修改
                    R.id.mIvEdit -> TodoEditActivity.start(mContext, data)
                    // 删除
                    R.id.mIvDelete -> {
                        DialogHelper.confirm("是否删除这条清单？") {
                            mViewModel.todoDelete(data.id)
                        }
                    }
                }
            }
        }
        // 查
        mViewModel.listLiveData.observe(this, this::onListResponse)
        // 修改状态
        mViewModel.statusLiveData.observe(this, this::onStatusResponse)
        // 删除
        mViewModel.deleteLiveData.observe(this, this::onDeleteResponse)
        // 清单数据更新
        BusManager.todoLiveData.observe(this, this::onChangeEvent)
        // 用户登录监听
        BusManager.userLiveData.observe(this, this::onLoginResponse)
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
        mAdapter.showEmpty(data.isEmpty(), EmptyView.EMPTY_TODO)
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        filter.pageNum = data.curPage + 1
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

    /**
     * 修改Todo状态
     * @param data Zip2<Int, TodoStatus>
     */
    private fun onStatusResponse(data: Zip2<Int, TodoStatus>) {
        val index = mAdapter.data.indexOfFirst { it.id == data.first }
        mAdapter.data[index].status = data.second.value
        mAdapter.data[index].completeDateStr = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd"))
        mAdapter.notifyItemChanged(index)
    }

    /**
     * 删除Todo返回
     * @param id Int
     */
    private fun onDeleteResponse(id: Int) {
        mAdapter.removeAt(mAdapter.data.indexOfFirst { it.id == id })
        if (mAdapter.data.size == 0) mBinding.mRefreshLayout.autoRefresh()
    }

    /**
     * 新增或者修改Todo通知
     * @param info TodoInfo
     */
    private fun onChangeEvent(info: TodoInfo) {
        mBinding.mRefreshLayout.autoRefresh()
    }


    /**
     * 监听用户登录
     * @param login Boolean
     */
    private fun onLoginResponse(login: Boolean) {
        this.start()
    }

}