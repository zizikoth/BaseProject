package com.memo.base.common.activity

import android.content.Context
import com.memo.base.R
import com.memo.base.base.BaseActivity
import com.memo.base.databinding.ActivityWebBinding
import com.memo.base.utils.web.WebHelper
import com.memo.core.utils.ext.startActivity

/**
 * title:通用网页
 * describe:
 *
 * @author memo
 * @date 2023-01-13 11:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebActivity : BaseActivity<ActivityWebBinding>() {

    companion object {
        fun start(context: Context, title: String, url: String) {
            context.startActivity<WebActivity>("title" to title, "url" to url)
        }
    }

    private var title:String = ""
    private var url:String = ""

    /*** 初始化 ***/
    override fun initialize() {
        title = intent.getStringExtra("title")?:""
        url = intent.getStringExtra("url")?:""
        mBinding.mTitleBar.setTitle(title)
        WebHelper.init(mContext, mBinding.mContainer, R.layout.layout_delegate_web_error, url)
    }
}