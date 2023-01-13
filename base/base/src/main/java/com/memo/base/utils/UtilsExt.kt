package com.memo.base.utils

import android.view.Gravity
import androidx.annotation.StringRes
import com.blankj.utilcode.util.ToastUtils
import com.memo.base.manager.DataManager
import com.memo.base.manager.RouteManager
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.string

/**
 * toast调用
 * @param message Any? 内容
 */
fun toast(message: Any?) {
    message?.let {
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0).setDurationIsLong(false).show(it.toString())
    }
}

/**
 * 检查是否登录
 * @param naviToLogin Boolean 是否需要跳转登录
 * @return Boolean
 */
fun checkLogin(naviToLogin:Boolean = true): Boolean {
    if(DataManager.isLogin()){
        return true
    }else if(naviToLogin){
        DialogHelper.confirm("该功能需要登录后使用，是否立即登录？"){
            RouteManager.startAccountActivity()
        }
    }
    return false
}

