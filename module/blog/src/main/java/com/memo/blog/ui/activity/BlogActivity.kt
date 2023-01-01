package com.memo.blog.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.memo.blog.databinding.ActivityBlogBinding
import com.memo.blog.ui.adapter.BlogFragmentAdapter
import com.memo.blog.viewmodel.BlogViewModel
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.remote.Chapter
import com.memo.base.manager.RouteManager
import com.memo.base.utils.indicator.ElasticLineIndicator
import com.memo.base.utils.indicator.init

/**
 * title:公众号界面
 * describe:
 *
 * @author memo
 * @date 2022-12-09 15:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Route(path = RouteManager.BlogActivity)
class BlogActivity : BaseVmActivity<BlogViewModel, ActivityBlogBinding>() {

    private var cid: Int = 0
    private val mAdapter = BlogFragmentAdapter(this)

    /*** 初始化数据 ***/
    override fun initData() {
        cid = intent.getIntExtra("cid", cid)
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {

            mIndicator.init(mViewPager, ElasticLineIndicator(mContext))

            mViewPager.offscreenPageLimit = 3

        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            val chapter = mAdapter.getData()[mBinding.mViewPager.currentItem]
            BlogSearchActivity.start(mContext, chapter.id, chapter.name)
        }

        mViewModel.chapterLiveData.observe(this, this::onChapterResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getChapters()
    }

    /**
     * 公众号返回
     * @param data ArrayList<Chapter>
     */
    private fun onChapterResponse(data: ArrayList<Chapter>) {
        // Tab
        (mBinding.mIndicator.navigator as ElasticLineIndicator).setTitles(data.map { it.name })

        // ViewPager
        mAdapter.setData(data)
        mBinding.mViewPager.adapter = mAdapter
        val index = data.indexOfFirst { cid == it.id }
        mBinding.mViewPager.setCurrentItem(if (index == -1) 0 else index, false)
    }
}