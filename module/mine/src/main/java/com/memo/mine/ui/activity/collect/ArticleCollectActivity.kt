package com.memo.mine.ui.activity.collect

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.business.R
import com.memo.business.base.BaseVmActivity
import com.memo.business.common.activity.WebActivity
import com.memo.business.common.adapter.ArticleAdapter
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.utils.finish
import com.memo.business.utils.onItemChildClick
import com.memo.business.utils.showEmpty
import com.memo.business.utils.toast
import com.memo.mine.databinding.ActivityArticleCollectBinding
import com.memo.mine.viewmodel.CollectViewModel

/**
 * title:我的站内收藏文章
 * describe:
 *
 * @author memo
 * @date 2022-12-13 22:06
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ArticleCollectActivity : BaseVmActivity<CollectViewModel, ActivityArticleCollectBinding>() {

    private var pageNum: Int = 0

    private val mAdapter = ArticleAdapter(true)

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
            mViewModel.getCollectArticles(pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getCollectArticles(pageNum)
        }

        mAdapter.onItemChildClick { id, data ->
            when (id) {
                R.id.mItemDelete -> mViewModel.unCollectArticleInCollect(data.id, data.originId)
                R.id.mItemEdit -> toast("修改")
                R.id.mItemArticle -> WebActivity.start(mContext, data.link, data.title)
            }
        }

        mViewModel.articleListLiveData.observe(this, this::onArticleResponse)
        mViewModel.deleteLiveData.observe(this, this::onUnCollectArticle)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getCollectArticles(pageNum)
    }

    /**
     * 文章数据返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

    /**
     * 移除收藏文章
     * @param articleId Int
     */
    private fun onUnCollectArticle(articleId: Int) {
        val index = mAdapter.data.indexOfFirst { it.id == articleId }
        mAdapter.removeAt(index)
    }
}