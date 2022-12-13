package com.memo.mine.ui.activity.square

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.business.R
import com.memo.business.base.BaseVmActivity
import com.memo.business.common.activity.WebActivity
import com.memo.business.common.adapter.ArticleAdapter
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.manager.DataManager
import com.memo.business.utils.finish
import com.memo.business.utils.onItemChildClick
import com.memo.business.utils.onItemClick
import com.memo.business.utils.showEmpty
import com.memo.core.utils.DialogHelper
import com.memo.mine.databinding.ActivitySquareBinding
import com.memo.mine.ui.activity.share.ShareActivity
import com.memo.mine.viewmodel.ShareViewModel

/**
 * title:广场
 * describe:
 *
 * @author memo
 * @date 2022-12-13 21:04
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SquareActivity : BaseVmActivity<ShareViewModel, ActivitySquareBinding>() {

    private var pageNum: Int = 0

    private val mAdapter = ArticleAdapter()

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
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 0
            mViewModel.getSquareArticles(pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getSquareArticles(pageNum)
        }

        mAdapter.onItemChildClick { id, data -> if (id == R.id.mIvIcon) ShareActivity.start(mContext, data.userId) }
        mAdapter.onItemClick { WebActivity.start(mContext, it.link, it.title) }

        mViewModel.articleLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getSquareArticles(pageNum)
    }

    /**
     * 文章数据返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        if (DataManager.showSquareTip()) {
            DialogHelper.alert("点击头像查看更多用户分享文章")
        }
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }
}