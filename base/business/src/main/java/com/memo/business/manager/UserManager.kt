package com.memo.business.manager

import UserInfo
import com.blankj.utilcode.util.SPUtils
import com.memo.business.config.Config
import com.memo.core.utils.GsonHelper
import okhttp3.HttpUrl.Companion.toHttpUrl
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cookie.ICookieJar

/**
 * title:用户信息管理
 * describe:
 *
 * @author memo
 * @date 2022-03-18 11:47
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object UserManager {
    private val SP by lazy { SPUtils.getInstance(SP_NAME) }

    private const val SP_NAME = "WanAndroid_Memo"
    private const val USER_INFO = "USER_INFO"

    private var userInfo: UserInfo? = null

    fun setUser(userInfo: UserInfo) {
        this.userInfo = userInfo
        SP.put(USER_INFO, GsonHelper.parse2Json(userInfo))
    }

    fun getUser(): UserInfo? {
        if (this.userInfo == null) {
            val json = SP.getString(USER_INFO)
            if (!json.isNullOrEmpty()) {
                this.userInfo = GsonHelper.parse2Bean<UserInfo>(json)
            }
        }
        return this.userInfo
    }

    private fun removeUser(){
        SP.remove(USER_INFO)
    }

    fun hasCookie(): Boolean {
        val cookieJar = RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar
        val loadCookie = cookieJar.loadCookie(Config.baseUrl.toHttpUrl())
        return !loadCookie.isNullOrEmpty()
    }

    private fun removeCookie(){
       ( RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar).removeAllCookie()
    }

    fun loginOut(){
        userInfo = null
        removeUser()
        removeCookie()
    }

}