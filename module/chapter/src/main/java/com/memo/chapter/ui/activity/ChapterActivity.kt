package com.memo.chapter.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.entity.remote.Chapter
import com.memo.business.manager.RouteManager
import com.memo.business.utils.indicator.ElasticLineIndicator
import com.memo.business.utils.indicator.init
import com.memo.chapter.databinding.ActivityChapterBinding
import com.memo.chapter.ui.adapter.ChapterFragmentAdapter
import com.memo.chapter.viewmodel.ChapterViewModel

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
@Route(path = RouteManager.ChapterActivity)
class ChapterActivity : BaseVmActivity<ChapterViewModel, ActivityChapterBinding>() {

    private var cid: Int = -1
    private val mAdapter = ChapterFragmentAdapter(this)

    /*** 初始化数据 ***/
    override fun initData() {
        cid = intent.getIntExtra("chapterId", cid)
        LogUtils.iTag("chapter", cid)
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {

            mIndicator.init(mViewPager, ElasticLineIndicator(mContext))

            mViewPager.offscreenPageLimit = 7

        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            val chapter = mAdapter.getData()[mBinding.mViewPager.currentItem]
            ChapterSearchActivity.start(mContext, chapter.id, chapter.name)
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
        mAdapter.setChapters(data)
        mBinding.mViewPager.adapter = mAdapter
        val index = data.indexOfFirst { cid == it.id }
        mBinding.mViewPager.setCurrentItem(if (index == -1) 0 else index,false)
    }
}