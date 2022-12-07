package com.memo.business.app

import com.memo.business.manager.InitManager
import com.memo.core.core.CoreApp

/**
 * title:项目Application
 * describe:
 *
 * @author memo
 * @date 2022-03-18 13:41
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class App : CoreApp() {
    override fun onCreate() {
        super.onCreate()
        InitManager.initInApp(this)
        InitManager.initInSplash()
    }
}