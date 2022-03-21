package com.memo.business.config

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

enum class RunMode(val description: String) {
    Release("线上地址"),
    Debug("测试地址"),
    LocalLuYao("路遥本地"),
    LocalYangZhuang("杨壮本地"),
    LocalYunGen("运根本地"),
    LocalXuYang("旭阳本地")
}

object Config {
    /*** 是否开启调试 ***/
    const val debug: Boolean = true

    /*** 运行模式 ***/
    var runMode = RunMode.Debug

    @DefaultDomain
    @JvmField
    var baseUrl: String = when (runMode) {
        RunMode.Release -> "https://server.shwread.cn/whgcserver/server"
        RunMode.Debug -> "https://test.shwread.cn:8088/whgcserver/server"
        RunMode.LocalLuYao -> "http://192.168.31.220:9099/whgcserver/server"
        RunMode.LocalYangZhuang -> "http://192.168.31.146:9099/whgcserver/server"
        RunMode.LocalYunGen -> "http://192.168.31.135:9099/whgcserver/server"
        RunMode.LocalXuYang -> "http://192.168.31.6:9099/whgcserver/server"
    }


}