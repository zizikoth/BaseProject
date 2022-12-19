package com.memo.main.ui.fragment.home

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.appbar.AppBarLayout
import com.memo.business.base.BaseVmFragment
import com.memo.business.common.activity.WebActivity
import com.memo.business.common.adapter.ArticleAdapter
import com.memo.business.entity.local.Zip5Null
import com.memo.business.entity.remote.*
import com.memo.business.manager.RouteManager
import com.memo.business.utils.finish
import com.memo.business.utils.onItemClick
import com.memo.business.utils.showEmpty
import com.memo.core.utils.ext.margin
import com.memo.core.utils.ext.onClick
import com.memo.main.databinding.FragmentHomeBinding
import com.memo.main.ui.adapter.BannerAdapter
import com.memo.main.viewmodel.HomeViewModel
import kotlin.math.abs

/**
 * title:首页
 * describe:
 *
 * @author memo
 * @date 2022-12-08 10:50
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class HomeFragment : BaseVmFragment<HomeViewModel, FragmentHomeBinding>() {

    private var pageNum: Int = 0

    private val mAdapter by lazy { ArticleAdapter() }

    /*** 初始化数据 ***/
    override fun initData() {
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            // 设置沉浸式偏移
            mToolBar.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
            mIvSearch.margin(0, BarUtils.getStatusBarHeight(), 0, 0)
            // 轮播图
            mBanner.registerLifecycleObserver(lifecycle).setAdapter(BannerAdapter()).create()
            // 文章列表
            mRvList.run {
                layoutManager = GridLayoutManager(mActivity, 4)
                mAdapter.setGridSpanSizeLookup { _, viewType, _ ->
                    if (viewType == ARTICLE_TYPE_CHAPTER) 1 else 4
                }
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            // 设置AppBar的透明度
            mAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBar, offset ->
                mToolBar.alpha = abs(offset).toFloat() / appBar.totalScrollRange
            })
            // 标题栏点击
            mTitleBar.setOnTitleClickListener {
                // 点击标题 滑动到顶部
                if (mToolBar.alpha >= 1f) {
                    val behavior = (mAppBar.layoutParams as CoordinatorLayout.LayoutParams).behavior
                    if (behavior is AppBarLayout.Behavior) {
                        behavior.topAndBottomOffset = 0
                    }
                    mRvList.scrollToPosition(0)
                }
            }
            // 搜索点击
            mIvSearch.onClick { RouteManager.startSearchActivity() }
            // 刷新加载
            mRefreshLayout.setOnRefreshListener {
                pageNum = 0
                mViewModel.getData()
            }
            mRefreshLayout.setOnLoadMoreListener {
                mViewModel.getArticles(pageNum)
            }
            // 轮播图
            mBanner.setOnPageClickListener { _, position ->
                val data = mBanner.data[position] as Article
                WebActivity.startFromList(mActivity, data.url, data.id, data.title)
            }
            // 列表点击
            mAdapter.onItemClick {
                when (it.multiItemType) {
                    ARTICLE_TYPE_CHAPTER -> RouteManager.startBlogActivity(it.id)
                    ARTICLE_TYPE_TITLE -> if (it.showMore) RouteManager.startNewProjectActivity()
                    ARTICLE_TYPE_ARTICLE -> WebActivity.startFromList(mActivity, it.link, it.id, it.title)
                }
            }
            mAdapter.onProjectClick = { WebActivity.startFromList(mActivity, it.link, it.id, it.title) }
        }

        mViewModel.liveData.observe(this, this::onDataResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getData()
    }

    /**
     * 所有数据返回
     * @param data Zip4Null<ArrayList<Article>, ArrayList<Chapter>, ArrayList<Article>, ListEntity<Article>>, ListEntity<Article>>
     * 轮播图 公众号 置顶文章 最新项目 首页文章
     */
    private fun onDataResponse(data: Zip5Null<ArrayList<Article>, ArrayList<Chapter>, ArrayList<Article>, ListEntity<Article>, ListEntity<Article>>) {
        if (pageNum == 0) {
            val list = arrayListOf<Article>()
            // 轮播图
            if (!data.first.isNullOrEmpty()) {
                mBinding.mBanner.create(data.first!!)
            }
            // 公众号
            if (!data.second.isNullOrEmpty()) {
                val chapters = data.second!!.map { Article(title = it.name, id = it.id, multiItemType = ARTICLE_TYPE_CHAPTER) }
                if (chapters.size > 7) {
                    list.addAll(chapters.subList(0, 7))
                    list.add(Article(title = "更多", id = 0, multiItemType = ARTICLE_TYPE_CHAPTER))
                } else {
                    list.addAll(chapters)
                }
            }
            // 置顶文章
            if (!data.third.isNullOrEmpty()) {
                list.add(Article(title = "置顶文章", showMore = false, multiItemType = ARTICLE_TYPE_TITLE))
                data.third!!.forEach { it.top = true }
                list.addAll(data.third!!)
            }
            // 最新项目
            if (data.forth != null && !data.forth!!.isEmpty()) {
                list.add(Article(title = "最新项目", showMore = true, multiItemType = ARTICLE_TYPE_TITLE))
                list.add(Article(projects = data.forth!!.datas, multiItemType = ARTICLE_TYPE_PROJECT))
            }
            // 首页文章
            if (data.fifth != null && !data.fifth!!.isEmpty()) {
                val articles = data.fifth!!
                mBinding.mRefreshLayout.finish(articles.hasMore())
                pageNum = articles.curPage
                list.add(Article(title = "推荐阅读", showMore = false, multiItemType = ARTICLE_TYPE_TITLE))
                list.addAll(articles.datas)
            }
            mAdapter.setList(list)
        } else {
            this.onArticleResponse(data.fifth)
        }
    }

    /**
     * 资讯内容返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>?) {
        if (data != null && !data.isEmpty()) {
            mAdapter.showEmpty(data.isEmpty())
            mAdapter.addData(data.datas)
            pageNum = data.curPage
            mBinding.mRefreshLayout.finish(data.hasMore())
        }
    }

}