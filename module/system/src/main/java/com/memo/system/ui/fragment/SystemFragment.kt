package com.memo.system.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.memo.business.base.BaseFragment
import com.memo.business.manager.RouteManager
import com.memo.business.utils.indicator.RoundCoverIndicator
import com.memo.business.utils.indicator.init
import com.memo.core.adapter.BaseFragmentPager2Adapter
import com.memo.core.utils.ext.addElevation
import com.memo.system.databinding.FragmentSystemBinding

/**
 * title:系统
 * describe:
 *
 * @author memo
 * @date 2022-12-12 09:34
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Route(path = RouteManager.SystemFragment)
class SystemFragment : BaseFragment<FragmentSystemBinding>() {

    private val titles = listOf("体  系", "导  航")

    /*** 初始化 ***/
    override fun initialize() {
        mBinding.run {
            root.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
            mFlTop.addElevation()
            val indicator = RoundCoverIndicator(mActivity)
            indicator.setNaviTitles(listOf("体  系", "导  航"))
            mIndicator.init(mViewPager, indicator)
            mViewPager.adapter = BaseFragmentPager2Adapter(mActivity).apply {
                setData(listOf(CategoryFragment(), NavigationFragment()))
            }
        }
    }
}