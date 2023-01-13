package com.memo.search.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.base.BaseVmFragment
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.utils.finish
import com.memo.base.utils.onItemClick
import com.memo.base.utils.showEmpty
import com.memo.base.widget.EmptyView
import com.memo.search.databinding.FragmentSearchArticleBinding
import com.memo.search.viewmodel.SearchViewModel

/**
 * title:搜索文章界面
 * describe:
 *
 * @author memo
 * @date 2022-12-10 14:43
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SearchArticleFragment : BaseVmFragment<SearchViewModel, FragmentSearchArticleBinding>() {

    private var pageNum: Int = 0
    private var searchWord: String = ""
    private val mAdapter = ArticleAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mRvArticle.run {
                layoutManager = LinearLayoutManager(mActivity)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 0
            mViewModel.searchArticle(searchWord, pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.searchArticle(searchWord, pageNum)
        }

        mAdapter.onItemClick {
            ArticleActivity.startFromList(mActivity, it.title, it.link, it.id)
        }

        mViewModel.articleLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.searchArticle(searchWord, pageNum)
    }

    /**
     * 关键词搜索
     * @param keyword String
     */
    fun search(keyword: String) {
        searchWord = keyword
        // 如果当前页面没有准备好 等待运行流程到start
        if (isPrepared) {
            mPageState.showLoadingView()
            pageNum = 0
            mViewModel.searchArticle(keyword, pageNum)
        }
    }

    /**
     * 搜索文章返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty(),EmptyView.EMPTY_SEARCH)
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }
}