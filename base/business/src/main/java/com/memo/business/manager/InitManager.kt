package com.memo.business.manager

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.dylanc.loadingstateview.LoadingStateView
import com.frame.core.utils.OOMHelper
import com.kongzue.dialogx.DialogX
import com.memo.business.config.Config
import com.memo.business.widget.state.LoadingDelegate
import com.memo.business.widget.state.NetErrorDelegate
import com.memo.business.widget.state.ServerErrorDelegate
import com.memo.core.utils.GsonHelper
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.converter.GsonConverter
import rxhttp.wrapper.cookie.CookieStore
import java.io.File


/**
 * title:初始化管理
 * describe:
 *
 * @author memo
 * @date 2022-03-18 11:54
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object InitManager {
    fun initInApp(app: Application) {
        if (ProcessUtils.isMainProcess()) {
            // 工具类
            Utils.init(app)
            LogUtils.getConfig().isLogSwitch = Config.debug

            // RxHttp
            val client = RxHttpPlugins.getOkHttpClient().newBuilder()
                .cookieJar(CookieStore(File(app.externalCacheDir, "HttpCookie")))
                .build()
            RxHttpPlugins.init(client)
                .setConverter(GsonConverter.create(GsonHelper.getGson()))
                .setDebug(Config.debug, true)
        }
    }

    fun initInSplash() {
        // oom检测
        OOMHelper.startMonitorLowMemory()
        // Dialog
        DialogX.DEBUGMODE = Config.debug
        DialogX.init(Utils.getApp())
        // LoadingStateView
        LoadingStateView.setViewDelegatePool {
            this.register(LoadingDelegate(), NetErrorDelegate(), ServerErrorDelegate())
        }
    }
}
