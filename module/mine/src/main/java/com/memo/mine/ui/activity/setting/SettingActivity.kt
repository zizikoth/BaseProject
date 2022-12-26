package com.memo.mine.ui.activity.setting

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.CleanUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.FileUtils
import com.memo.business.base.BaseVmActivity
import com.memo.business.manager.BusManager
import com.memo.business.manager.RouteManager
import com.memo.business.utils.toast
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.onClick
import com.memo.mine.databinding.ActivitySettingBinding
import com.memo.mine.viewmodel.MineViewModel

/**
 * title:设置
 * describe:
 *
 * @author memo
 * @date 2022-12-26 14:23
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SettingActivity : BaseVmActivity<MineViewModel, ActivitySettingBinding>() {

    override fun showContent(): Boolean = true

    /*** 初始化数据 ***/
    override fun initData() {
        mBinding.run {
            val size = FileUtils.getLength(mContext.externalCacheDir) + FileUtils.getLength(mContext.cacheDir)
            mItemClear.setExtra(ConvertUtils.byte2FitMemorySize(size, 2))
        }
    }

    /*** 初始化控件 ***/
    override fun initView() {
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.mItemClear.onClick {
            CleanUtils.cleanInternalCache()
            CleanUtils.cleanExternalCache()
            mBinding.mItemClear.setExtra("")
            toast("缓存清理完毕")
        }
        mBinding.mTvLoginOut.onClick {
            DialogHelper.confirm("是否确定退出登录？"){
                mViewModel.loginOut()
                BusManager.userLiveData.post(false)
                RouteManager.startAccountActivity()
                finish()
            }
        }
    }

    /*** 页面开始请求 ***/
    override fun start() {
    }

}