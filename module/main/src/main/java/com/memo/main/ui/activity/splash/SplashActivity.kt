package com.memo.main.ui.activity.splash

import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.BarUtils
import com.memo.base.manager.InitManager
import com.memo.core.core.CoreActivity
import com.memo.core.utils.ext.startActivity
import com.memo.main.databinding.ActivitySplashBinding
import com.memo.main.ui.activity.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * title:启动页
 * describe:
 *
 * @author memo
 * @date 2022-06-01 11:09
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SplashActivity : CoreActivity<ActivitySplashBinding>() {

    override fun init() {
        // 页面配置
        BarUtils.transparentStatusBar(this)
        BarUtils.setNavBarVisibility(this, false)
        BarUtils.setStatusBarLightMode(this, false)
        // 延迟进入首页
        this.lifecycleScope.launch {
            delay(1000L)
            startActivity<MainActivity>()
            finish()
        }
    }
}