package com.memo.project

import com.blankj.utilcode.util.AppUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.base.observe
import com.memo.business.config.Config
import com.memo.business.config.RunMode
import com.memo.business.entity.local.Zip2Null
import com.memo.business.utils.toast
import com.memo.core.utils.ClickHelper
import com.memo.project.databinding.ActivityMainBinding

class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

    override fun initData() {}

    override fun initView() {
    }

    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            Config.runMode = RunMode.Release
            start()
        }
        observe(mViewModel.dataLiveData, this::onArticle)
    }

    override fun start() {
        mViewModel.getHomeData()
    }

    private fun onArticle(data: Zip2Null<ArrayList<Article>, ArticleListEntity>) {
        data.first?.let {
            // 轮播图
            mBinding.mTvInfo.text = "轮播图数量" + it.size
        }

        data.second?.let {
            // 文章
            mBinding.mTvInfo.text = mBinding.mTvInfo.text.toString() + "\t文章数量" + it.size
        }
    }

    override fun onBackPressed() {
        if (ClickHelper.isDoubleClickExit { toast("再次点击退出应用") }) {
            AppUtils.exitApp()
        }
    }

}