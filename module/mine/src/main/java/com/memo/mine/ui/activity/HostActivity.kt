package com.memo.mine.ui.activity

import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.FragmentUtils
import com.memo.business.base.BaseActivity
import com.memo.business.entity.remote.UserInfo
import com.memo.business.manager.DataManager
import com.memo.mine.R
import com.memo.mine.databinding.ActivityHostBinding
import com.memo.mine.ui.fragment.MineFragment
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

class HostActivity : BaseActivity<ActivityHostBinding>() {
    /*** 初始化 ***/
    override fun initialize() {
        lifecycleScope.launch {
            RxHttp.postForm("user/login").add("username", "Mr.Memo").add("password", "zhx931024")
                .toFlowResponse<UserInfo>().collect {
                    DataManager.setUser(it)
                    FragmentUtils.add(supportFragmentManager, MineFragment(), R.id.mFlContainer)
                }
        }
    }

}