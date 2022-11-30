package com.memo.business.utils

import com.blankj.utilcode.util.ToastUtils
import com.kongzue.dialogx.dialogs.TipDialog

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
fun toast(message: Any?) {
    message?.let {
        ToastUtils.showShort(it.toString())
    }
}

