package com.memo.search.ui.fragment

import com.blankj.utilcode.util.LogUtils
import com.google.android.flexbox.FlexboxLayoutManager
import com.memo.base.base.BaseVmFragment
import com.memo.base.entity.remote.HotKey
import com.memo.base.utils.onItemClick
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.gone
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.setVisible
import com.memo.core.utils.ext.visible
import com.memo.search.databinding.FragmentSearchWordBinding
import com.memo.search.ui.activity.SearchActivity
import com.memo.search.ui.adapter.SearchWordAdapter
import com.memo.search.viewmodel.SearchViewModel

/**
 * title:搜索关键词界面
 * describe:
 *
 * @author memo
 * @date 2022-12-10 14:43
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SearchWordFragment : BaseVmFragment<SearchViewModel, FragmentSearchWordBinding>() {

    private val mHistoryAdapter = SearchWordAdapter()

    private val mHotAdapter = SearchWordAdapter()

    override fun showContent() = true

    /*** 初始化数据 ***/
    override fun initData() {
        mBinding.run {
            mRvHistory.run {
                layoutManager = FlexboxLayoutManager(mActivity)
                adapter = mHistoryAdapter
            }

            mRvHot.run {
                layoutManager = FlexboxLayoutManager(mActivity)
                adapter = mHotAdapter
            }
        }
    }

    /*** 初始化控件 ***/
    override fun initView() {
        val history = mViewModel.getLocalHistory()
        LogUtils.iTag("history", history, history.size)
        mHistoryAdapter.setList(history)
        mBinding.mLlHistory.setVisible(history.isNotEmpty())
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mIvClear.onClick {
                DialogHelper.confirm("是否删除搜索历史记录？") {
                    mViewModel.clearLocalHistory()
                    mLlHistory.gone()
                }
            }
        }
        mHistoryAdapter.onItemClick {
            this.addWord(it)
            (mActivity as SearchActivity).search(it)
        }
        mHotAdapter.onItemClick {
            this.addWord(it)
            (mActivity as SearchActivity).search(it)
        }

        mViewModel.hotLiveData.observe(this, this::onHotResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getHotKey()
    }

    /**
     * 获取搜索热词返回
     * @param data ArrayList<HotKey>
     */
    private fun onHotResponse(data: ArrayList<HotKey>) {
        mHotAdapter.setList(data.map { it.name })
    }

    /**
     * 新增搜索关键词
     * @param keyword String
     */
    fun addWord(keyword: String) {
        mHistoryAdapter.setList(mViewModel.addLocalHistory(keyword))
        mBinding.mLlHistory.visible()
    }
}