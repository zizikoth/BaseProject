package com.memo.web

import android.content.Context
import android.view.KeyEvent
import com.just.agentweb.AgentWeb
import com.memo.business.base.BaseActivity
import com.memo.core.utils.extra.startActivity
import com.memo.web.databinding.ActivityWebBinding

class WebActivity : BaseActivity<ActivityWebBinding>() {

    companion object {
        fun start(context: Context, url: String, title: String) {
            context.startActivity<WebActivity>("url" to url, "title" to title)
        }
    }

    /*** 网址 ***/
    private var url: String = ""

    /*** 标题 ***/
    private var title: String = ""

    private var mAgentWeb: AgentWeb? = null

    override fun initialize() {
        url = intent.getStringExtra("url") ?: ""
        title = intent.getStringExtra("title") ?: ""
        mBinding.mTitleBar.setTitle(title)
        mAgentWeb = WebHelper.init(mContext, mBinding.mFlContainer, url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (WebHelper.onKeyDown(mAgentWeb, keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        WebHelper.onResume(mAgentWeb)
        super.onResume()
    }

    override fun onPause() {
        WebHelper.onPause(mAgentWeb)
        super.onPause()
    }

    override fun onDestroy() {
        WebHelper.onDestroy(mAgentWeb)
        super.onDestroy()
    }
}