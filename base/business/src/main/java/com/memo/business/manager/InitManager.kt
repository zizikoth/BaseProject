package com.memo.business.manager

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.frame.core.utils.OOMHelper
import com.kongzue.dialogx.DialogX
import com.memo.business.config.Config
import com.memo.business.widget.state.LoadingDelegate
import com.memo.business.widget.state.NetErrorDelegate
import com.memo.business.widget.state.ServerErrorDelegate
import com.memo.core.utils.GsonHelper
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.converter.GsonConverter


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
    fun initFirst(app: Application) {
        if (ProcessUtils.isMainProcess()) {
            // 工具类
            Utils.init(app)
            LogUtils.getConfig().isLogSwitch = Config.debug

            // RxHttp
            RxHttpPlugins.init(RxHttpPlugins.getOkHttpClient())
                .setConverter(GsonConverter.create(GsonHelper.getGson()))
                .setDebug(Config.debug,true)


        }
    }

    fun initLater() {
        // oom检测
        OOMHelper.startMonitorLowMemory()
        // Dialog
        DialogX.DEBUGMODE = Config.debug
        DialogX.init(Utils.getApp())
        // LoadingStateView
        LoadingStateView.setViewDelegatePool {
            this.register(ViewType.LOADING, LoadingDelegate())
            this.register(ViewType.EMPTY, NetErrorDelegate())
            this.register(ViewType.ERROR, ServerErrorDelegate())
        }
    }
}
