package com.memo.project

import com.blankj.utilcode.util.AppUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.base.observe
import com.memo.business.config.Config
import com.memo.business.config.RunMode
import com.memo.business.manager.InitManager
import com.memo.business.utils.toast
import com.memo.core.utils.ClickHelper
import com.memo.core.utils.extra.onClick
import com.memo.project.databinding.ActivityMainBinding
import com.memo.web.WebActivity

class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

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
                if (Config.runMode == RunMode.Debug) Config.runMode = RunMode.Local else Config.runMode = RunMode.Debug
                mTitleBar.setRightText(if (Config.runMode == RunMode.Debug) "当前测试接口" else "当前本地接口")
            }

            mTvInfo.onClick {
                WebActivity.start(mContext, "https://www.baidu.com", "百度")
            }

            mBtnRequest.onClick { mViewModel.getOnceRequest() }
            mBtnConcat.onClick { mViewModel.getConcatRequest() }
            mBtnCombine.onClick { mViewModel.getCombineRequest() }
        }

        observe(mViewModel.dataLiveData, this::onArticle)
    }

    override fun start() {
        mViewModel.getOnceRequest()
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