package com.memo.blog.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.blog.databinding.FragmentBlogItemBinding
import com.memo.base.base.BaseVmFragment
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.utils.finish
import com.memo.base.utils.onItemClick
import com.memo.base.utils.showEmpty
import com.memo.blog.viewmodel.BlogViewModel
import com.memo.core.utils.ext.withArguments

/**
 * title:公众号内的文章
 * describe:
 *
 * @author memo
 * @date 2022-12-09 16:21
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class BlogItemFragment : BaseVmFragment<BlogViewModel, FragmentBlogItemBinding>() {

    companion object {
        fun newInstance(chapterId: Int) = BlogItemFragment().withArguments("cid" to chapterId)
    }

    /*** 公众号Id ***/
    private var cid: Int = 0

    /*** 页码 ***/
    private var pageNum: Int = 1
    private val mAdapter = ArticleAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
        cid = arguments?.getInt("cid", cid) ?: cid
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mRvList.run {
                layoutManager = LinearLayoutManager(mActivity)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        // 条目点击
        mAdapter.onItemClick {
            ArticleActivity.startFromList(mActivity, it.title, it.link, it.id)
        }
        // 刷新
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 1
            mViewModel.getChapterArticle(cid, pageNum)
        }
        // 加载
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getChapterArticle(cid, pageNum)
        }
        // 列表返回
        mViewModel.articleLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getChapterArticle(cid, pageNum)
    }

    /**
     * 公众号文章返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage + 1
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

}