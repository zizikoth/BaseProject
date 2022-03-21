package com.memo.project

import com.memo.business.base.BaseVmActivity
import com.memo.business.base.observe
import com.memo.business.entity.local.Zip2Null
import com.memo.business.utils.toast
import com.memo.core.utils.extra.onClick
import com.memo.project.databinding.ActivityMainBinding

class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

    override fun initData() {}

    override fun initView() {}

    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            toast("设置")
        }
        mBinding.mIconItemCell.onClick{
            toast("提示")
        }
        observe(mViewModel.dataLiveData, this::onInform)
    }

    override fun start() {
        mViewModel.getAll()
    }

    private fun onInform(data: Zip2Null<Banner, Inform>) {
        mBinding.mTvInfo.text = data.first?.list?.first()?.title + data.second?.list?.first()?.title
    }

}