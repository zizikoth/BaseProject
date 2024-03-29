package com.memo.core.utils

import androidx.annotation.FloatRange
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

    fun hideLoad() {
        WaitDialog.dismiss()
    }

    fun progress(message: String, @FloatRange(from = 0.0, to = 1.0) progress: Float) {
        WaitDialog.show(message, progress)
    }

    fun tipSuccess(message: String) {
        TipDialog.show(message, WaitDialog.TYPE.SUCCESS)
    }

    fun tipError(message: String) {
        TipDialog.show(message, WaitDialog.TYPE.ERROR)
    }

    fun popTip(message: String) {
        PopTip.show(message).autoDismiss(1500)
    }

    fun alert(message: String,onPositive: (() -> Unit)? = null) {
        MessageDialog.show("提示", message, "确定").setOkButtonClickListener { _, _ ->
            onPositive?.invoke()
            false
        }
    }

    fun confirm(message: String, onPositive: () -> Unit) {
        MessageDialog.show("提示", message, "确定", "取消").setOkButtonClickListener { _, _ ->
                onPositive.invoke()
                false
            }
    }

    fun bottom(title: String, array: Array<String>, selectIndex: Int, listener: (position: Int) -> Unit) {
        BottomMenu.show(array).setTitle(title).setSelection(selectIndex).setOnMenuItemClickListener { _, _, index ->
                listener.invoke(index)
                false
            }
    }

}