package com.memo.mine.ui.activity.square

import androidx.recyclerview.widget.LinearLayoutManager
import com.kongzue.dialogx.dialogs.GuideDialog
import com.memo.base.R
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.activity.ArticleActivity
import com.memo.base.common.adapter.ArticleAdapter
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.manager.DataManager
import com.memo.base.utils.finish
import com.memo.base.utils.onItemChildClick
import com.memo.base.utils.showEmpty
import com.memo.mine.databinding.ActivitySquareBinding
import com.memo.mine.ui.activity.share.ShareActivity
import com.memo.mine.viewmodel.ShareViewModel
import com.memo.mine.viewmodel.SquareViewModel

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
class SquareActivity : BaseVmActivity<SquareViewModel, ActivitySquareBinding>() {

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
        // 刷新
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 0
            mViewModel.getSquareShareArticles(pageNum)
        }
        // 加载
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getSquareShareArticles(pageNum)
        }
        // 点击
        mAdapter.onItemChildClick { id, data ->
            if (id == R.id.mIvIcon) ShareActivity.start(mContext, data.userId)
            else if (id == R.id.mItemArticle) ArticleActivity.startFromList(mContext, data.title, data.link, data.id)
        }
        // 监听列表
        mViewModel.listLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getSquareShareArticles(pageNum)
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

        // 点击头像的图示
        if(DataManager.showSquareTip()){
            mBinding.mRvList.postDelayed({
                mAdapter.getViewByPosition(0, R.id.mIvIcon)?.let {
                    GuideDialog.show(it, com.memo.mine.R.drawable.icon_click_more)
                }
            },200)
        }

    }
}