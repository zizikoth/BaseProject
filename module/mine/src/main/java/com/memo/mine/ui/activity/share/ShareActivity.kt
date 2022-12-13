package com.memo.mine.ui.activity.share

import android.content.Context
import com.memo.business.base.BaseVmActivity
import com.memo.core.utils.ext.startActivity
import com.memo.mine.databinding.ActivityShareBinding
import com.memo.mine.viewmodel.ShareViewModel

/**
 * title:用户分享文章
 * describe:
 *
 * @author memo
 * @date 2022-12-13 21:14
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ShareActivity : BaseVmActivity<ShareViewModel, ActivityShareBinding>() {

    companion object {
        fun start(context: Context, userId: Int) {
            context.startActivity<ShareActivity>("userId" to userId)
        }
    }

    private var pageNum: Int = 1
    private var userId: Int = 0

    /*** 初始化数据 ***/
    override fun initData() {
        userId = intent.getIntExtra("userId", 0)
    }

    /*** 初始化控件 ***/
    override fun initView() {
    }

    /*** 初始化监听 ***/
    override fun initListener() {
    }

    /*** 页面开始请求 ***/
    override fun start() {
    }

}