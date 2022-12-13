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
    Release("线上地址", "https://www.wanandroid.com"),
    Debug("测试地址", "https://www.wanandroid.com"),
    Local("本地地址", "https://www.wanandroid.com")
}

object Config {
    /*** 是否开启调试 ***/
    const val debug: Boolean = true

    /*** 运行模式 ***/
    var runMode = RunMode.Debug
        set(value) {
            LogUtils.iTag("切换运行模式", "from = $field", "to = $value")
            baseUrl = value.url
            field = value
        }

    @DefaultDomain
    @JvmField
    var baseUrl: String = runMode.url

}