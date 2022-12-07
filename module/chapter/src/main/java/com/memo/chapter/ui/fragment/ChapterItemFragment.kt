package com.memo.chapter.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.business.base.BaseVmFragment
import com.memo.business.common.activity.ArticleActivity
import com.memo.business.common.adapter.ArticleAdapter
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.utils.finish
import com.memo.business.utils.onItemClick
import com.memo.business.utils.showEmpty
import com.memo.chapter.databinding.FragmentChapterItemBinding
import com.memo.chapter.viewmodel.ChapterViewModel
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
class ChapterItemFragment : BaseVmFragment<ChapterViewModel, FragmentChapterItemBinding>() {

    companion object {
        fun newInstance(chapterId: Int) = ChapterItemFragment().withArguments("cid" to chapterId)
    }

    private var cid: Int = -1
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
        mAdapter.onItemClick {
            ArticleActivity.start(mActivity, it.link, it.id, it.title)
        }
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 1
            mViewModel.getChapterArticle(cid, pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getChapterArticle(cid, pageNum)
        }

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