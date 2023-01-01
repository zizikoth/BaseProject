package com.memo.core.utils.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ConvertUtils
import com.memo.core.core.CoreApp

/**
 * title: 获取资源文件
 * describe:
 *
 * @author zhou
 * @date 2019-01-29 14:43
 */

fun color(@ColorRes id: Int): Int = ContextCompat.getColor(CoreApp.app.applicationContext, id)

fun string(@StringRes id: Int): String = CoreApp.app.applicationContext.resources.getString(id)

fun stringArray(@ArrayRes id: Int): Array<String> = CoreApp.app.applicationContext.resources.getStringArray(id)

fun drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(CoreApp.app.applicationContext, id)

fun dimen(@DimenRes id: Int) = CoreApp.app.applicationContext.resources.getDimension(id)

val Int.dp2px get() = ConvertUtils.dp2px(this.toFloat())
val Float.dp2px get() = ConvertUtils.dp2px(this)
val Int.sp2px get() = ConvertUtils.sp2px(this.toFloat())
val Float.sp2px get() = ConvertUtils.sp2px(this)


fun FragmentActivity.showFragment(@IdRes container: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .add(container, fragment)
        .commit()
}

fun Fragment.showFragment(@IdRes container: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction()
        .add(container, fragment)
        .commit()
}

/**
 * 复制到粘贴板
 * @param content 内容
 */
fun Context.copyToClipboard(content: String) {
    val plainText = ClipData.newPlainText("Copy", content)
    val clipboardManager =
        ContextCompat.getSystemService(this.applicationContext, ClipboardManager::class.java)
    clipboardManager?.setPrimaryClip(plainText)
}

/**
 * 从粘贴板上获取复制数据
 */
fun Context.getFirstFromClipboard(): String {
    val clipData = ContextCompat.getSystemService(
        this.applicationContext,
        ClipboardManager::class.java
    )?.primaryClip
    return if (clipData != null && clipData.itemCount > 0) {
        clipData.getItemAt(0).text.toString()
    } else {
        ""
    }
}




