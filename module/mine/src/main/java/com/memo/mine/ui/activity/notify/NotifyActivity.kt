package com.memo.mine.ui.activity.notify

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.activity.WebActivity
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.NotifyMessage
import com.memo.base.utils.finish
import com.memo.base.utils.onItemClick
import com.memo.base.utils.showEmpty
import com.memo.base.widget.EmptyView
import com.memo.core.utils.ext.startActivity
import com.memo.mine.databinding.ActivityNotifyBinding
import com.memo.mine.ui.adapter.NotifyAdapter
import com.memo.mine.viewmodel.NotifyViewModel
import okhttp3.internal.notify

/**
 * title:个人消息
 * describe:
 *
 * @author memo
 * @date 2023-01-01 16:47
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class NotifyActivity : BaseVmActivity<NotifyViewModel, ActivityNotifyBinding>() {

    companion object {
        fun start(context: Context, read: Boolean) {
            context.startActivity<NotifyActivity>("read" to read)
        }
    }

    // 是否已读 false-未读 true-已读
    private var read: Boolean = false

    private var pageNum: Int = 1

    private val mAdapter = NotifyAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
        read = intent.getBooleanExtra("read", false)
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            // 未读才显示
            mTitleBar.showRight(!read)

            mRvList.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mTitleBar.setOnRightClickListener {
                NotifyActivity.start(mContext, true)
            }
            mRefreshLayout.setOnRefreshListener {
                pageNum = 1
                start()
            }
            mRefreshLayout.setOnLoadMoreListener {
                start()
            }
        }

        mAdapter.onItemClick {
            if (it.fullLink.isNotEmpty()) WebActivity.start(mContext, it.fullLink, "消息通知")
        }

        mViewModel.listLiveData.observe(this, this::onListResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        if (read) {
            mViewModel.getReadList(pageNum)
        } else {
            mViewModel.getUnReadList(pageNum)
        }
    }

    /**
     * 通知消息返回
     * @param data ListEntity<NotifyMessage>
     */
    private fun onListResponse(data: ListEntity<NotifyMessage>) {
        mAdapter.showEmpty(data.isEmpty(),EmptyView.EMPTY_NOTIFY)
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

}