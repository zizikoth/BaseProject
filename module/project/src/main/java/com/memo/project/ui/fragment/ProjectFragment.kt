package com.memo.project.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.memo.business.base.BaseVmFragment
import com.memo.business.entity.remote.Chapter
import com.memo.business.manager.RouteManager
import com.memo.business.utils.indicator.ElasticLineIndicator
import com.memo.business.utils.indicator.init
import com.memo.core.utils.ext.addElevation
import com.memo.core.utils.ext.fromHtml
import com.memo.project.databinding.FragmentProjectBinding
import com.memo.project.ui.adapter.ProjectFragmentAdapter
import com.memo.project.viewmodel.ProjectViewModel
import java.util.*

/**
 * title:项目模块
 * describe:
 *
 * @author memo
 * @date 2022-12-10 18:35
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Route(path = RouteManager.ProjectFragment)
class ProjectFragment : BaseVmFragment<ProjectViewModel, FragmentProjectBinding>() {


    /*** 初始化数据 ***/
    override fun initData() {}

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            root.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
            mFlTop.addElevation()
            mIndicator.init(mViewPager, ElasticLineIndicator(mActivity))
            mViewPager.offscreenPageLimit = 3
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mViewModel.chapterLiveData.observe(this, this::onChapterResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getProjects()
    }

    /**
     * 项目类型返回
     * @param data ArrayList<Chapter>
     */
    private fun onChapterResponse(data: ArrayList<Chapter>) {
        // Tab
        (mBinding.mIndicator.navigator as ElasticLineIndicator).setTitles(data.map { it.name.fromHtml() })
        // ViewPager
        val mAdapter = ProjectFragmentAdapter(mActivity)
        mAdapter.setData(data)
        mBinding.mViewPager.adapter = mAdapter
    }

}