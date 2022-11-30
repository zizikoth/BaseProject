package com.memo.project

import com.blankj.utilcode.util.AppUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.base.observe
import com.memo.business.config.Config
import com.memo.business.config.RunMode
import com.memo.business.entity.local.Zip2Null
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.manager.InitManager
import com.memo.business.utils.toast
import com.memo.core.utils.ClickHelper
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.extra.onClick
import com.memo.project.databinding.ActivityMainBinding
import com.memo.web.WebActivity

class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

    override fun doOnBefore() {
        InitManager.initInSplash()
        super.doOnBefore()
    }

    override fun initData() {}

    override fun initView() {
    }

    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            mBinding.mTitleBar.setRightText(if(Config.runMode == RunMode.Release) "正式接口" else "测试接口")
            if(Config.runMode == RunMode.Release) Config.runMode = RunMode.Debug else Config.runMode = RunMode.Release
            start()
        }
        mBinding.mTvInfo.onClick {
            WebActivity.start(mContext, "https://www.baidu.com", "百度")
        }
        observe(mViewModel.dataLiveData, this::onArticle)
    }

    override fun start() {
        mViewModel.getHomeData()
    }

    private fun onArticle(data: Zip2Null<ArrayList<Article>, ListEntity<Article>>) {
        data.first?.let {
            // 轮播图
            mBinding.mTvInfo.text = "轮播图数量" + it.size
        }

        data.second?.let {
            // 文章
            mBinding.mTvInfo.text = mBinding.mTvInfo.text.toString() + "\n文章数量" + it.size
        }
    }

    override fun onBackPressed() {
        if (ClickHelper.isDoubleClickExit { toast("再次点击退出应用") }) {
            AppUtils.exitApp()
        }
    }

}