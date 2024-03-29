package com.memo.core.utils

import android.os.Build
import com.blankj.utilcode.util.DeviceUtils
import com.memo.core.utils.ext.md5

/**
 * title:获取应用uuid
 * describe:
 *
 * @author memo
 * @date 2020-01-09 10:21
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object UUIDHelper {

    private var uuid: String = ""
    private var udid: String = ""


    fun getUUID(): String {
        if (uuid.isEmpty()) {
            uuid = StringBuilder()
                .append(Build.BOARD).append(Build.BRAND)
                .append(Build.DEVICE).append(Build.DISPLAY)
                .append(Build.FINGERPRINT)
                .append(Build.HARDWARE).append(Build.HOST)
                .append(Build.MANUFACTURER).append(Build.MODEL)
                .append(Build.PRODUCT).append(Build.TAGS)
                .append(Build.TYPE).append(Build.USER)
                .append(DeviceUtils.getAndroidID())
                .toString()
                .md5()
        }
        return uuid
    }

    fun getUDID(): String {
        if (udid.isEmpty()) {
            udid = DeviceUtils.getUniqueDeviceId()
        }
        return udid
    }
}