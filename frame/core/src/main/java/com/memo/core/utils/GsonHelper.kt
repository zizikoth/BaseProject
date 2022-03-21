package com.memo.core.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * title:
 * describe:
 *
 * @author zhou
 * @date 2019-05-31 17:17
 */
object GsonHelper {

    private var gson: Gson? = null

    /**
     * 获取Gson
     * @return Gson
     */
    @JvmStatic
    fun getGson(): Gson {
        if (gson == null) {
            gson = GsonBuilder()
                // 如果有一些特定的类也是可以同样设置默认值
                .registerTypeAdapter(String::class.java, StringTypeAdapter())
                .registerTypeAdapter(Int::class.java, IntTypeAdapter())
                .registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
                .registerTypeAdapter(Float::class.java, FloatTypeAdapter())
                .registerTypeAdapter(Long::class.java, LongTypeAdapter())
                .registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
                // 日期格式化
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                // 防止Html乱码
                .disableHtmlEscaping()
                // 格式化结果
                .setPrettyPrinting()
                .create()
        }
        return gson!!
    }

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

    class BooleanTypeAdapter : TypeAdapter<Boolean>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Boolean {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return false
            }
            return reader.nextBoolean()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: Boolean?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    class DoubleTypeAdapter : TypeAdapter<Double>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Double {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return 0.0
            }
            return reader.nextDouble()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: Double?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    class FloatTypeAdapter : TypeAdapter<Float>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Float {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return 0f
            }
            return reader.nextDouble().toFloat()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: Float?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    class IntTypeAdapter : TypeAdapter<Int>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Int {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return 0
            }
            return reader.nextInt()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: Int?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    class LongTypeAdapter : TypeAdapter<Long>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Long {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return 0L
            }
            return reader.nextLong()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: Long?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

    class StringTypeAdapter : TypeAdapter<String>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): String {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: String?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

}


