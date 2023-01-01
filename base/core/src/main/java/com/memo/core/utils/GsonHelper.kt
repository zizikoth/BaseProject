package com.memo.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hjq.gson.factory.GsonFactory

/**
 * title:
 * describe:
 *
 * @author zhou
 * @date 2019-05-31 17:17
 */
object GsonHelper {

    /**
     * 获取Gson
     * @return Gson
     */
    @JvmStatic
    fun getGson(): Gson = GsonFactory.getSingletonGson()

    /**
     * 解析成字符串
     */
    @JvmStatic
    fun parse2Json(any: Any): String = getGson().toJson(any)

    /**
     * 解析实体类
     */
    @JvmStatic
    inline fun <reified T> parse2Bean(json: String): T = getGson().fromJson(json, T::class.java)

    /**
     * 解析列表
     */
    @JvmStatic
    inline fun <reified T> parse2List(json: String): ArrayList<T> =
        getGson().fromJson(json, TypeToken.getParameterized(ArrayList::class.java, T::class.java).type)

}


