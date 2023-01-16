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

    /*** 关键字页面 ***/
    private val mWordFragment = SearchWordFragment()

    /*** 搜索文章页面 ***/
    private val mArticleFragment = SearchArticleFragment()
    private val mAdapter = BaseFragmentPager2Adapter(this)

    /*** 初始化 ***/
    override fun initialize() {
        mBinding.run {
            mTitleBar.setOnLeftClickListener {
                this@SearchActivity.onBackPressed()
            }

            // 判断如果搜索框没有文字的时候切换到关键字页面
            mSearchBar.setOnTextChangeListener {
                if (it.isEmpty()) mViewPager.currentItem = 0
            }

            // 搜索
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

    /**
     * 搜索关键字
     * @param keyword String    关键字
     */
    fun search(keyword: String) {
        mBinding.mViewPager.currentItem = 1
        mBinding.mSearchBar.keyword = keyword
        mArticleFragment.search(keyword)
    }

    /**
     * 点击返回键
     */
    override fun onBackPressed() {
        if (mBinding.mViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mBinding.mSearchBar.keyword = ""
            mBinding.mViewPager.currentItem = 0
        }
    }

}