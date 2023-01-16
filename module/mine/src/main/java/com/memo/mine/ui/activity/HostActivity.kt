package com.memo.mine.ui.activity

import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.FragmentUtils
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseActivity
import com.memo.base.entity.remote.UserInfo
import com.memo.base.manager.DataManager
import com.memo.mine.R
import com.memo.mine.databinding.ActivityHostBinding
import com.memo.mine.ui.fragment.MineFragment
import kotlinx.coroutines.launch
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:模块宿主
 * describe:
 *
 * @author memo
 * @date 2023-01-16 13:55
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class HostActivity : BaseActivity<ActivityHostBinding>() {
    /*** 初始化 ***/
    override fun initialize() {
        lifecycleScope.launch {
            ApiRepository.login("Mr.Memo", "zhx931024").collect {
                DataManager.setUser(it)
                FragmentUtils.add(supportFragmentManager, MineFragment(), R.id.mFlContainer)
            }
        }
    }

}