package com.memo.business.config

import androidx.annotation.Keep
import com.blankj.utilcode.util.LogUtils
import rxhttp.wrapper.annotation.DefaultDomain

/**
 * title:配置文件
 * describe:
 *
 * @author memo
 * @date 2022-03-17 10:00
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */

@Keep
enum class RunMode(val description: String, val url: String) {
    Release("线上地址", "https://server.shwread.cn/whgcserver/server"),
    Debug("测试地址", "https://test.shwread.cn:8088/whgcserver/server"),
    LocalLuYao("路遥本地", "http://192.168.31.220:9099/whgcserver/server"),
    LocalYangZhuang("杨壮本地", "http://192.168.31.146:9099/whgcserver/server"),
    LocalYunGen("运根本地", "http://192.168.31.135:9099/whgcserver/server"),
    LocalXuYang("旭阳本地", "http://192.168.31.6:9099/whgcserver/server")
}

object Config {
    /*** 是否开启调试 ***/
    const val debug: Boolean = true

    /*** 运行模式 ***/
    var runMode = RunMode.Debug
        set(value) {
            baseUrl = value.url
            field = value
        }

    @DefaultDomain
    @JvmField
    var baseUrl: String = runMode.url

}