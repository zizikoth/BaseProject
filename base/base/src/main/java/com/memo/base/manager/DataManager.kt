package com.memo.base.manager

import com.blankj.utilcode.util.SPUtils
import com.memo.base.config.Config
import com.memo.base.entity.remote.UserInfo
import com.memo.core.utils.GsonHelper
import okhttp3.HttpUrl.Companion.toHttpUrl
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cookie.ICookieJar

/**
 * title:全局本地信息管理
 * describe:
 *
 * @author memo
 * @date 2022-03-18 11:47
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object DataManager {

    private const val SP_NAME = "WanAndroid_Memo"
    private const val USER_INFO = "USER_INFO"
    private const val SEARCH_HISTORY = "SEARCH_HISTORY"
    private const val SQUARE_TIP = "SQUARE_TIP"

    val SP by lazy { SPUtils.getInstance(SP_NAME) }

    private var searchHistory: ArrayList<String> = arrayListOf()
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
    fun isLogin(): Boolean {
        val cookieJar = RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar
        val loadCookie = cookieJar.loadCookie(Config.baseUrl.toHttpUrl())
        return !loadCookie.isNullOrEmpty() && getUser() != null
    }

    /**
     * 移除cookie
     */
    private fun removeCookie() {
        (RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar).removeAllCookie()
    }

    /**
     * 获取搜索记录
     * @return ArrayList<String>
     */
    fun getSearchHistory(): ArrayList<String> {
        val history = SP.getString(SEARCH_HISTORY)
        return if (history.isNullOrEmpty()) arrayListOf() else ArrayList(history.split(","))
    }

    /**
     * 添加搜索记录
     * @param keyword String
     */
    fun addSearchHistory(keyword: String): ArrayList<String> {
        val history = this.getSearchHistory()
        if (history.contains(keyword)) {
            history.remove(keyword)
        }
        history.add(0, keyword)
        if (history.size > 10) {
            history.removeLast()
        }
        SP.put(SEARCH_HISTORY, history.joinToString(","))
        return history
    }

    /**
     * 清除搜索记录
     */
    fun clearSearchHistory() {
        SP.remove(SEARCH_HISTORY)
    }

    /**
     * 是否显示广场提示
     */
    fun showSquareTip(): Boolean {
        val showTip = SP.getBoolean(SQUARE_TIP, true)
        if (showTip) SP.put(SQUARE_TIP, false)
        return showTip
    }

    /**
     * 退出登录
     */
    fun loginOut() {
        userInfo = null
        removeCookie()
        SP.clear(true)
    }

}