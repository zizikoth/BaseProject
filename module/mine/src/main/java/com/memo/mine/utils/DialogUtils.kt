package com.memo.mine.utils

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.memo.base.api.ApiErrorHandler
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.WebUrl
import com.memo.base.manager.RouteManager
import com.memo.base.utils.toast
import com.memo.core.utils.DialogHelper
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.value
import com.memo.mine.R

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-16 14:06
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object DialogUtils {

    /**
     * 显示新增收藏文章弹窗
     * @param addAction Function3<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
     */
    fun showAddArticleDialog(addAction: (title: String, author: String, link: String) -> Unit) {
        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.layout_dialog_article_collect) {
            override fun onBind(dialog: FullScreenDialog, v: View) {
                v.findViewById<TextView>(R.id.mTvTitle).text = "新增"
                val mEtTitle = v.findViewById<EditText>(R.id.mEtTitle)
                val mEtAuthor = v.findViewById<EditText>(R.id.mEtAuthor)
                val mEtLink = v.findViewById<EditText>(R.id.mEtLink)
                v.findViewById<View>(R.id.mBtnSubmit).onClick {
                    if (mEtTitle.value.isEmpty()) {
                        toast("请输入收藏文章标题")
                    } else if (mEtAuthor.value.isEmpty()) {
                        toast("请输入收藏文章作者")
                    } else if (mEtLink.value.isEmpty()) {
                        toast("请输入收藏文章地址")
                    } else {
                        addAction.invoke(mEtTitle.value, mEtAuthor.value, mEtLink.value)
                        dialog.dismiss()
                    }
                }
            }
        })
    }

    /**
     * 显示修改收藏文章弹窗
     * @param data Article
     * @param editAction Function4<[@kotlin.ParameterName] Int, [@kotlin.ParameterName] String, [@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
     */
    fun showEditArticleDialog(data: Article, editAction: (id: Int, title: String, author: String, link: String) -> Unit) {
        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.layout_dialog_article_collect) {
            override fun onBind(dialog: FullScreenDialog, v: View) {
                v.findViewById<TextView>(R.id.mTvTitle).text = "修改"
                val mEtTitle = v.findViewById<EditText>(R.id.mEtTitle).apply { value = data.title }
                val mEtAuthor = v.findViewById<EditText>(R.id.mEtAuthor).apply { value = data.author }
                val mEtLink = v.findViewById<EditText>(R.id.mEtLink).apply { value = data.link }
                v.findViewById<View>(R.id.mBtnSubmit).onClick {
                    if (mEtTitle.value.isEmpty()) {
                        toast("请输入收藏文章标题")
                    } else if (mEtAuthor.value.isEmpty()) {
                        toast("请输入收藏文章作者")
                    } else if (mEtLink.value.isEmpty()) {
                        toast("请输入收藏文章地址")
                    } else {
                        editAction.invoke(data.id, mEtTitle.value, mEtAuthor.value, mEtLink.value)
                        dialog.dismiss()
                    }
                }
            }
        })
    }


    /**
     * 显示新增网址收藏弹窗
     * @param addAction Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
     */
    fun showAddWebUrlDialog(addAction: (name: String, link: String) -> Unit) {
        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.layout_dialog_weburl_collect) {
            override fun onBind(dialog: FullScreenDialog, v: View) {
                v.findViewById<TextView>(R.id.mTvTitle).text = "新增"
                val mEtName = v.findViewById<EditText>(R.id.mEtName)
                val mEtLink = v.findViewById<EditText>(R.id.mEtLink)
                v.findViewById<View>(R.id.mBtnSubmit).onClick {
                    if (mEtName.value.isEmpty()) {
                        toast("请输入收藏网址名称")
                    } else if (mEtLink.value.isEmpty()) {
                        toast("请输入收藏网址地址")
                    } else {
                        addAction.invoke(mEtName.value, mEtLink.value)
                        dialog.dismiss()
                    }
                }
            }
        })
    }


    /**
     * 显示修改网址收藏弹窗
     * @param data WebUrl
     * @param editAction Function3<[@kotlin.ParameterName] Int, [@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
     */
    fun showEditWebUrlDialog(data: WebUrl, editAction: (id: Int, name: String, link: String) -> Unit) {
        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.layout_dialog_weburl_collect) {
            override fun onBind(dialog: FullScreenDialog, v: View) {
                v.findViewById<TextView>(R.id.mTvTitle).text = "修改"
                val mEtName = v.findViewById<EditText>(R.id.mEtName).apply { value = data.name }
                val mEtLink = v.findViewById<EditText>(R.id.mEtLink).apply { value = data.link }
                v.findViewById<View>(R.id.mBtnSubmit).onClick {
                    if (mEtName.value.isEmpty()) {
                        toast("请输入收藏网址名称")
                    } else if (mEtLink.value.isEmpty()) {
                        toast("请输入收藏网址地址")
                    } else {
                        editAction.invoke(data.id, mEtName.value, mEtLink.value)
                        dialog.dismiss()
                    }
                }
            }
        })
    }

}