package com.memo.system.ui.activity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.activity.WebActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.utils.finish
import com.memo.base.utils.onItemClick
import com.memo.base.utils.showEmpty
import com.memo.core.utils.ext.startActivity
import com.memo.system.databinding.ActivityCategoryArticleBinding
import com.memo.system.viewmodel.SystemViewModel

/**
 * title:体系类别文章
 * describe:
 *
 * @author memo
 * @date 2022-12-12 09:57
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CategoryArticleActivity : BaseVmActivity<SystemViewModel, ActivityCategoryArticleBinding>() {

    companion object {
        fun start(context: Context, cid: Int, title: String) {
            context.startActivity<CategoryArticleActivity>("cid" to cid, "title" to title)
        }
    }

    private var cid: Int = 0
    private var title: String = ""
    private var pageNum: Int = 0

    private val mAdapter = ArticleAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
        cid = intent.getIntExtra("cid", cid)
        title = intent.getStringExtra("title").orEmpty()
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mTitleBar.setTitle(title)
            mRvList.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mAdapter.onItemClick {
            WebActivity.start(mContext, it.link, it.title)
        }

        mViewModel.articleLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getSystemArticle(cid, pageNum)
    }

    /**
     * 体系分类文章返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }
}