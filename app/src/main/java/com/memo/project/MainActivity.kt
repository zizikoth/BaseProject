package com.memo.project

import com.blankj.utilcode.util.AppUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.base.observe
import com.memo.business.config.Config
import com.memo.business.config.RunMode
import com.memo.business.manager.InitManager
import com.memo.business.utils.toast
import com.memo.core.utils.ClickHelper
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.extra.onClick
import com.memo.project.databinding.ActivityMainBinding

class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

    private val envArray = arrayOf("正式环境", "测试环境", "本地环境")
    private var envIndex = 0

    override fun doOnBefore() {
        InitManager.initInSplash()
        super.doOnBefore()
    }

    override fun showContent() = true

    override fun initData() {}

    override fun initView() {
    }

    override fun initListener() {
        mBinding.run {
            mTitleBar.setOnRightClickListener {
                DialogHelper.bottom("运行环境", envArray, envIndex) {
                    envIndex = it
                    Config.runMode = if (it == 0) RunMode.Release else if (it == 1) RunMode.Debug else RunMode.Local
                    mTitleBar.setRightText(envArray[it])
                }
            }
            mBtnRequest.onClick { mViewModel.getOnceRequest() }
            mBtnConcat.onClick { mViewModel.getConcatRequest() }
            mBtnCombine.onClick { mViewModel.getCombineRequest() }
        }

        observe(mViewModel.dataLiveData, this::onArticle)
    }

    override fun start() {
    }


    private fun onArticle(data: String) {
        mBinding.mTvInfo.text = data
    }

    override fun onBackPressed() {
        if (ClickHelper.isDoubleClickExit { toast("再次点击退出应用") }) {
            AppUtils.exitApp()
        }
    }

}