package com.memo.main.ui.activity.main

import com.blankj.utilcode.util.AppUtils
import com.memo.core.utils.FragmentHelper
import com.memo.business.base.BaseActivity
import com.memo.business.utils.toast
import com.memo.core.utils.ClickHelper
import com.memo.main.R
import com.memo.main.databinding.ActivityMainBinding
import com.memo.main.ui.fragment.home.HomeFragment

/**
 * title:首页
 * describe:
 *
 * @author memo
 * @date 2022-06-01 14:43
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun transparentStatusBar() = true

    override fun initialize() {
        mBinding.run {
            val fragmentHelper = FragmentHelper(mContext, R.id.mContainer, supportFragmentManager)
            fragmentHelper.add(HomeFragment(),HomeFragment(),HomeFragment(),HomeFragment())
                .show()

            mLottieBar.setOnItemChangeListener {
                fragmentHelper.show(it)
            }
        }
    }

    override fun onBackPressed() {
        if (ClickHelper.isDoubleClickExit { toast("再次点击退出应用") }) {
            AppUtils.exitApp()
        }
    }

}