package com.memo.main.ui.activity.account

import com.memo.business.base.BaseVmActivity
import com.memo.business.entity.remote.UserInfo
import com.memo.business.manager.BusManager
import com.memo.business.manager.UserManager
import com.memo.business.utils.indicator.RoundCoverIndicator
import com.memo.business.utils.indicator.init
import com.memo.business.utils.toast
import com.memo.core.adapter.BaseFragmentAdapter
import com.memo.core.utils.ext.dimen
import com.memo.main.databinding.ActivityAccountBinding
import com.memo.main.ui.fragment.account.LoginFragment
import com.memo.main.ui.fragment.account.RegisterFragment
import com.memo.main.viewmodel.AccountViewModel

class AccountActivity : BaseVmActivity<AccountViewModel, ActivityAccountBinding>() {

    override fun showContent(): Boolean = true
    override fun transparentStatusBar(): Boolean = true

    override fun initData() {
    }

    override fun initView() {
        mBinding.run {
            val indicator = RoundCoverIndicator(mContext)
            indicator.setTitles(listOf("登 录", "注 册"))
            mIndicator.init(mViewPager, indicator)
            mViewPager.run {
                isUserInputEnabled = false
                offscreenPageLimit = 2
                adapter = BaseFragmentAdapter(mContext, arrayListOf(LoginFragment(), RegisterFragment()))
            }
        }
    }

    override fun initListener() {
        mViewModel.liveData.observe(this, this::onLoginSuccess)
    }

    override fun start() {
    }

    /**
     * 登录
     * @param username String   用户名
     * @param password String   密码
     */
    fun login(username: String, password: String) {
        if (username.isEmpty()) toast("请输入用户名")
        else if (password.isEmpty()) toast("请输入密码")
        else mViewModel.login(username, password)
    }

    /**
     * 注册
     * @param username String   用户名
     * @param password String   密码
     * @param rePassword String 重复密码
     */
    fun register(username: String, password: String, rePassword: String) {
        if (username.isEmpty()) toast("请输入用户名")
        else if (password.isEmpty()) toast("请输入密码")
        else if (password != rePassword) toast("两次输入的密码不一致")
        else mViewModel.register(username, password)
    }

    /**
     * 登录成功返回
     * @param userInfo UserInfo 用户信息
     */
    private fun onLoginSuccess(userInfo: UserInfo) {
        UserManager.setUser(userInfo)
        BusManager.userLiveData.post(userInfo)
        finish()
    }

}