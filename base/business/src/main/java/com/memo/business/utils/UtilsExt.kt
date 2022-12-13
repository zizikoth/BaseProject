package com.memo.business.utils

import android.view.Gravity
import androidx.annotation.StringRes
import com.blankj.utilcode.util.ToastUtils
import com.memo.business.manager.DataManager
import com.memo.business.manager.RouteManager
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.string

/**
 * title:简化调用
 * describe:
 *
 * @author memo
 * @date 2022-03-18 10:53
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
fun toast(@StringRes messageId:Int){
    toast(string(messageId))
}

fun toast(message: Any?) {
    message?.let {
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0).setDurationIsLong(false).show(it.toString())
    }
}

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

