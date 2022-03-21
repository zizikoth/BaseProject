package com.memo.project

import com.blankj.utilcode.util.AppUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.base.observe
import com.memo.business.config.Config
import com.memo.business.config.RunMode
import com.memo.business.entity.local.Zip2Null
import com.memo.business.utils.toast
import com.memo.core.utils.ClickHelper
import com.memo.core.utils.extra.onClick
import com.memo.core.widget.WaterMark
import com.memo.project.databinding.ActivityMainBinding

class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

    override fun initData() {}

    override fun initView() {
        WaterMark.add(this, "周个大个侠")
    }

    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            Config.runMode = RunMode.LocalLuYao
            start()
        }
        mBinding.mIconItemCell.onClick {
            WaterMark.remove(this)
        }
        observe(mViewModel.dataLiveData, this::onInform)
    }

    override fun start() {
        mViewModel.getAll()
    }

    private fun onInform(data: Zip2Null<Banner, Inform>) {
        mBinding.mTvInfo.text = data.first?.list?.first()?.title + data.second?.list?.first()?.title
    }


    override fun onBackPressed() {
        if (ClickHelper.isDoubleClickExit { toast("再次点击退出应用") }) {
            AppUtils.exitApp()
        }
    }

}