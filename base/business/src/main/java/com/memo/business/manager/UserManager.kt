package com.memo.business.manager

import com.blankj.utilcode.util.SPUtils
import com.memo.business.config.Config
import com.memo.business.entity.remote.UserInfo
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

    private const val SP_NAME = "WanAndroid_Memo"
    private const val USER_INFO = "USER_INFO"


    private val SP by lazy { SPUtils.getInstance(SP_NAME) }

    private var userInfo: UserInfo? = null

    /**
     * 设置用户
     * @param userInfo UserInfo
     */
    fun setUser(userInfo: UserInfo) {
        this.userInfo = userInfo
        SP.put(USER_INFO, GsonHelper.parse2Json(userInfo))
    }

    /**
     * 获取用户
     * @return UserInfo?
     */
    fun getUser(): UserInfo? {
        if (this.userInfo == null) {
            val json = SP.getString(USER_INFO)
            if (!json.isNullOrEmpty()) {
                this.userInfo = GsonHelper.parse2Bean<UserInfo>(json)
            }
        }
        return this.userInfo
    }

    private fun removeUser() {
        SP.remove(USER_INFO)
    }

    /**
     * 判断是否以收藏
     * @param id Int 文章id
     * @return Boolean
     */
    fun hasCollected(id: Int) = getUser()?.collectIds?.any { it == id } ?: false

    /**
     * 添加收藏id
     * @param id Int
     */
    fun addCollected(id: Int) {
        if (getUser()?.collectIds?.contains(id) == false) {
            getUser()?.collectIds?.add(id)
            getUser()?.let { setUser(it) }
        }
    }

    /**
     * 移除收藏
     * @param id Int
     */
    fun removeCollected(id: Int) {
        getUser()?.collectIds?.remove(id)
        getUser()?.let { setUser(it) }
    }

    /**
     * 判断是否存在cookie
     * @return Boolean
     */
    fun hasCookie(): Boolean {
        val cookieJar = RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar
        val loadCookie = cookieJar.loadCookie(Config.baseUrl.toHttpUrl())
        return !loadCookie.isNullOrEmpty()
    }

    /**
     * 移除cookie
     */
    private fun removeCookie() {
        (RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar).removeAllCookie()
    }

    /**
     * 退出登录
     */
    fun loginOut() {
        userInfo = null
        removeUser()
        removeCookie()
    }

}