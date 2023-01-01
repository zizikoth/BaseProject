package com.memo.project.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.activity.WebActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.manager.RouteManager
import com.memo.base.utils.finish
import com.memo.base.utils.onItemClick
import com.memo.base.utils.showEmpty
import com.memo.project.databinding.ActivityNewProjectBinding
import com.memo.project.viewmodel.ProjectViewModel

/**
 * title:项目界面
 * describe:
 *
 * @author memo
 * @date 2022-12-09 15:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Route(path = RouteManager.NewProjectActivity)
class NewProjectActivity : BaseVmActivity<ProjectViewModel, ActivityNewProjectBinding>() {

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
            mViewModel.getNewProjectArticles(pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getNewProjectArticles(pageNum)
        }
        mAdapter.onItemClick {
            WebActivity.startFromList(mContext, it.link, it.id, it.title)
        }

        mViewModel.articleLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getNewProjectArticles(pageNum)
    }

    /**
     * 文章返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 0) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }
}