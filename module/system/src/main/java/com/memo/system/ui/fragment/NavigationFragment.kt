package com.memo.system.ui.fragment

import com.google.android.flexbox.FlexboxLayoutManager
import com.memo.business.base.BaseVmFragment
import com.memo.business.common.activity.WebActivity
import com.memo.business.entity.remote.Navi
import com.memo.business.entity.remote.NodeItem
import com.memo.business.entity.remote.NodeItemChild
import com.memo.business.utils.supportsChangeAnimations
import com.memo.system.databinding.FragmentNavigationBinding
import com.memo.system.ui.adapter.SystemAdapter
import com.memo.system.viewmodel.SystemViewModel

/**
 * title:导航界面
 * describe:
 *
 * @author memo
 * @date 2022-12-12 11:02
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class NavigationFragment : BaseVmFragment<SystemViewModel, FragmentNavigationBinding>() {

    private val mAdapter = SystemAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.mRvList.run {
            supportsChangeAnimations = false
            layoutManager = FlexboxLayoutManager(mActivity)
            adapter = mAdapter
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mAdapter.childClickAction = {
            if (it is NodeItemChild) {
                WebActivity.start(mActivity, it.link, it.title)
            }
        }

        mViewModel.naviLiveData.observe(this, this::onNaviResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getNavi()
    }

    /**
     * 获取体系类别
     * @param data List<Chapter>
     */
    private fun onNaviResponse(data: List<Navi>) {
        val list = data.map {
            NodeItem(it.name, it.articles.map { child -> NodeItemChild(child.title, -1, child.link) }.toMutableList())
        }
        mAdapter.setList(list)
        // 第一个条目自动打开
        mBinding.mRvList.postDelayed({ mAdapter.autoClick(0) },200)
    }

}