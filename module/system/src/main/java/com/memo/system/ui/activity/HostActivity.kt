package com.memo.system.ui.activity

import com.blankj.utilcode.util.FragmentUtils
import com.memo.base.base.BaseActivity
import com.memo.system.R
import com.memo.system.databinding.ActivityHostBinding
import com.memo.system.ui.fragment.SystemFragment

/**
 * title:宿主界面
 * describe:
 *
 * @author memo
 * @date 2022-12-12 09:30
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class HostActivity : BaseActivity<ActivityHostBinding>() {
    override fun initialize() {
        FragmentUtils.add(supportFragmentManager, SystemFragment(), R.id.mFlContainer)
    }
}