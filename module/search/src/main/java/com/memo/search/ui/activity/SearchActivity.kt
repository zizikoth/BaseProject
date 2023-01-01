package com.memo.search.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.memo.base.base.BaseActivity
import com.memo.base.manager.RouteManager
import com.memo.core.adapter.BaseFragmentPager2Adapter
import com.memo.search.databinding.ActivitySearchBinding
import com.memo.search.ui.fragment.SearchArticleFragment
import com.memo.search.ui.fragment.SearchWordFragment

/**
 * title:搜索页面
 * describe:
 *
 * @author memo
 * @date 2022-12-10 14:11
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Route(path = RouteManager.SearchActivity)
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val mWordFragment = SearchWordFragment()
    private val mArticleFragment = SearchArticleFragment()
    private val mAdapter = BaseFragmentPager2Adapter(this)

    /*** 初始化 ***/
    override fun initialize() {
        mBinding.run {

            mSearchBar.setOnTextChangeListener {
                if (it.isEmpty()) mViewPager.currentItem = 0
            }

            mSearchBar.setOnSearchListener {
                if (it.isNotEmpty()) {
                    mWordFragment.addWord(it)
                    search(it)
                }
            }

            mViewPager.run {
                isUserInputEnabled = false
                offscreenPageLimit = 2
                mAdapter.setData(listOf(mWordFragment, mArticleFragment))
                adapter = mAdapter
            }
        }
    }

    fun search(keyword: String) {
        mBinding.mViewPager.currentItem = 1
        mBinding.mSearchBar.keyword = keyword
        mArticleFragment.search(keyword)
    }

    override fun finish() {
        if (mBinding.mViewPager.currentItem == 0) {
            super.finish()
        } else {
            mBinding.mSearchBar.keyword = ""
            mBinding.mViewPager.currentItem = 0
        }
    }
}