package com.memo.mine.ui.activity.collect

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.R
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.manager.BusManager
import com.memo.base.manager.DataManager
import com.memo.base.utils.finish
import com.memo.base.utils.onItemChildClick
import com.memo.base.utils.showEmpty
import com.memo.base.widget.EmptyView
import com.memo.mine.databinding.ActivityArticleCollectBinding
import com.memo.mine.utils.DialogUtils
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
    override fun initData() {}

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mTitleBar.setOnRightClickListener {
                DialogUtils.showAddArticleDialog { title, author, link ->
                    mViewModel.addOuterArticleCollect(title, author, link)
                }
            }
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
            mViewModel.getArticleCollectList(pageNum)
        }
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getArticleCollectList(pageNum)
        }

        mAdapter.onItemChildClick { viewId, data ->
            when (viewId) {
                // 在收藏列表删除收藏
                R.id.mItemDelete -> mViewModel.deleteCollectInCollect(data.id, data.originId)
                // 修改站外收藏文章
                R.id.mItemEdit -> DialogUtils.showEditArticleDialog(data) { id, title, author, link ->
                    mViewModel.editOuterArticleCollect(id, title, author, link)
                }
                // 跳转网页
                R.id.mItemArticle -> ArticleActivity.startFromCollect(mContext, data.title, data.link, data.originId, data.id)
            }
        }

        // 查
        mViewModel.listLiveData.observe(this, this::onListResponse)
        // 增
        mViewModel.addLiveData.observe(this, this::onAddResponse)
        // 改
        mViewModel.editLiveData.observe(this, this::onEditResponse)
        // 删
        mViewModel.deleteLiveData.observe(this, this::onDeleteResponse)
        // 更新了收藏
        BusManager.collectLiveData.observe(this, this::onCollectEvent)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getArticleCollectList(pageNum)
    }

    /**
     * 文章数据返回
     * @param data ListEntity<Article>
     */
    private fun onListResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty(),EmptyView.EMPTY_COLLECT)
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

    /**
     * 新增收藏
     * @param data Article
     */
    private fun onAddResponse(data: Article) {
        mAdapter.addData(data)
        DataManager.addCollected(data.id)
        BusManager.collectLiveData.post(true)
    }

    /**
     * 修改收藏
     * @param data Article
     */
    private fun onEditResponse(data: Article) {
        val index = mAdapter.data.indexOfFirst { it.id == data.id }
        mAdapter.getItemOrNull(index)?.let {
            it.title = data.title
            it.author = data.author
            it.link = data.link
            mAdapter.setData(index, it)
        }
    }

    /**
     * 移除收藏文章
     * @param id Int
     */
    private fun onDeleteResponse(id: Int) {
        mAdapter.removeAt(mAdapter.data.indexOfFirst { it.id == id })
        if (mAdapter.data.size == 0) mBinding.mRefreshLayout.autoRefresh()
        DataManager.removeCollected(id)
        BusManager.collectLiveData.post(true)
    }

    /**
     * 更新收藏数据
     * @param refresh Boolean
     */
    private fun onCollectEvent(refresh: Boolean) {
        mBinding.mRefreshLayout.autoRefresh()
    }
}