package com.memo.system.ui.fragment

import com.google.android.flexbox.FlexboxLayoutManager
import com.memo.business.base.BaseVmFragment
import com.memo.business.entity.remote.Category
import com.memo.business.entity.remote.NodeItem
import com.memo.business.entity.remote.NodeItemChild
import com.memo.business.utils.supportsChangeAnimations
import com.memo.system.databinding.FragmentCategoryBinding
import com.memo.system.ui.activity.CategoryArticleActivity
import com.memo.system.ui.adapter.SystemAdapter
import com.memo.system.viewmodel.SystemViewModel

/**
 * title:体系类别
 * describe:
 *
 * @author memo
 * @date 2022-12-12 11:01
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CategoryFragment : BaseVmFragment<SystemViewModel, FragmentCategoryBinding>() {

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
                CategoryArticleActivity.start(mActivity, it.id, it.title)
            }
        }
        mViewModel.chapterLiveData.observe(this, this::onSystemResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getSystem()
    }

    /**
     * 获取体系类别
     * @param data List<Chapter>
     */
    private fun onSystemResponse(data: List<Category>) {
        val list = data.map {
            NodeItem(it.name, it.children.map { child -> NodeItemChild(child.name, child.id, "") }.toMutableList())
        }
        mAdapter.setList(list)
        // 第一个条目自动打开
        mBinding.mRvList.postDelayed({ mAdapter.autoClick(0) },200)
    }

}