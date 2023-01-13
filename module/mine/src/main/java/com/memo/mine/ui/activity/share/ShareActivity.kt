package com.memo.mine.ui.activity.share

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.UserShareRecord
import com.memo.base.utils.finish
import com.memo.base.utils.onItemClick
import com.memo.base.utils.showEmpty
import com.memo.core.utils.ext.startActivity
import com.memo.mine.databinding.ActivityShareBinding
import com.memo.mine.viewmodel.ShareViewModel
import com.memo.mine.widget.HeaderShare

/**
 * title:用户分享文章
 * describe:
 *
 * @author memo
 * @date 2022-12-13 21:14
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ShareActivity : BaseVmActivity<ShareViewModel, ActivityShareBinding>() {

    companion object {
        fun start(context: Context, userId: Int) {
            context.startActivity<ShareActivity>("userId" to userId)
        }
    }

    private var pageNum: Int = 1
    private var userId: Int = 0

    /*** 头布局 ***/
    private val mHeaderView by lazy { HeaderShare(mContext) }

    private val mAdapter = ArticleAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
        userId = intent.getIntExtra("userId", 0)
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.mRvList.run {
            layoutManager = LinearLayoutManager(mContext)
            mAdapter.addHeaderView(mHeaderView)
            mAdapter.headerWithEmptyEnable = true
            adapter = mAdapter
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 1
            mViewModel.getUserShareArticles(userId, pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getUserShareArticles(userId, pageNum)
        }

        mAdapter.onItemClick { ArticleActivity.startFromList(mContext, it.title, it.link, it.id) }

        mViewModel.shareLiveData.observe(this, this::onShareResponse)

    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getUserShareArticles(userId, pageNum)
    }

    /**
     * 文章数据返回
     * @param data ListEntity<Article>
     */
    private fun onShareResponse(data: UserShareRecord) {
        val info = data.coinInfo
        mHeaderView.setName(info.nickname.ifEmpty { info.username })
            .setCoin(info.coinCount)
            .setLevel(info.level)
            .setRank(info.rank)

        val list = data.shareArticles
        mAdapter.showEmpty(list.isEmpty())
        if (list.curPage == 1) mAdapter.setList(list.datas) else mAdapter.addData(list.datas)
        pageNum = list.curPage+1
        mBinding.mRefreshLayout.finish(list.hasMore())
    }

}