package com.memo.business.manager

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.dylanc.loadingstateview.LoadingStateView
import com.kongzue.dialogx.DialogX
import com.memo.business.BuildConfig
import com.memo.business.R
import com.memo.business.config.Config
import com.memo.business.widget.state.LoadingDelegate
import com.memo.business.widget.state.NetErrorDelegate
import com.memo.business.widget.state.ServerErrorDelegate
import com.memo.core.utils.GsonHelper
import com.memo.core.utils.OOMHelper
import com.memo.core.utils.ext.color
import com.memo.core.utils.ext.dimen
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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
            val client =
                RxHttpPlugins.getOkHttpClient().newBuilder().cookieJar(CookieStore(File(app.externalCacheDir, "HttpCookie"))).build()
            RxHttpPlugins.init(client).setConverter(GsonConverter.create(GsonHelper.getGson())).setDebug(Config.debug, true)

            // ARouter
            if (BuildConfig.DEBUG) {
                ARouter.openLog()
                ARouter.openDebug()
            }
            ARouter.init(app)
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
        // SmartRefreshLayout
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.setEnableAutoLoadMore(false).setEnableOverScrollDrag(true)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            BallPulseFooter(context).setNormalColor(color(R.color.refresh_footer)).setAnimatingColor(color(R.color.refresh_footer)).apply {
                    minimumHeight = dimen(R.dimen.refresh_footer).toInt()
                }
        }
    }
}
