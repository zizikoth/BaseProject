package com.memo.main.ui.main

import com.memo.business.base.BaseVmActivity
import com.memo.main.databinding.ActivityMainBinding

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
class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {
    override fun initData() {
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun start() {
        mViewModel.getData()
    }

}