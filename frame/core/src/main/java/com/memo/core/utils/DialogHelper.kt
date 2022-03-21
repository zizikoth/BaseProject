package com.memo.core.utils

import androidx.annotation.FloatRange
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.*

/**
 * title:弹窗工具类
 * describe:
 *
 * @author memo
 * @date 2022-03-18 10:35
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object DialogHelper {

    fun showLoad(message: String = "加载中") {
        WaitDialog.show(message)
    }

    fun showLoadProgress(message: String, @FloatRange(from = 0.0, to = 1.0) progress: Float) {
        WaitDialog.show(message, progress)
    }

    fun hideLoad() {
        WaitDialog.dismiss()
    }

    fun tipSuccess(message: String) {
        TipDialog.show(message, WaitDialog.TYPE.SUCCESS)
    }

    fun tipError(message: String) {
        TipDialog.show(message, WaitDialog.TYPE.ERROR)
    }

    fun tip(message: String) {
        PopTip.show(message)
    }

    fun showAlertDialog(title: String, message: String, onPositive: () -> Unit) {
        MessageDialog.show(title, message, "确定", "取消")
            .setOkButtonClickListener { _, _ ->
                onPositive.invoke()
                false
            }
    }

    fun showBottomDialog(title: String, array: Array<String>, selectIndex: Int, listener: (position: Int) -> Unit) {
        BottomMenu.show(array)
            .setTitle(title)
            .setSelection(selectIndex)
            .setOnMenuItemClickListener { _, _, index ->
                listener.invoke(index)
                false
            }
    }

}