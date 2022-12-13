package com.memo.business.common.activity

import android.animation.Animator
import android.content.Context
import com.airbnb.lottie.LottieDrawable
import com.blankj.utilcode.util.LogUtils
import com.memo.business.R
import com.memo.business.base.BaseVmActivity
import com.memo.business.common.vm.ArticleViewModel
import com.memo.business.databinding.ActivityWebBinding
import com.memo.business.manager.DataManager
import com.memo.core.utils.ext.gone
import com.memo.core.utils.ext.startActivity
import com.memo.core.utils.ext.visible
import com.memo.core.utils.listener.SimpleAnimatorListener
import com.memo.web.WebHelper

/**
 * title:公共资讯网页
 * describe:
 *
 * @author memo
 * @date 2022-12-09 14:00
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebActivity : BaseVmActivity<ArticleViewModel, ActivityWebBinding>() {

    companion object {
        fun start(context: Context, url: String, title: String) {
            context.startActivity<WebActivity>("url" to url, "title" to title)
        }

        fun start(context: Context, url: String, articleId: Int, title: String) {
            context.startActivity<WebActivity>("url" to url, "articleId" to articleId, "title" to title)
        }

        fun start(context: Context, articleId: Int, originId: Int, title: String, url: String) {
            context.startActivity<WebActivity>("articleId" to articleId, "originId" to originId, "title" to title, "url" to url)
        }
    }

    /*** 网页地址 ***/
    private var url: String = ""

    /*** 网页标题 ***/
    private var title: String = ""

    /*** 资讯id ***/
    private var articleId: Int = 0

    /*** 资讯初始id ***/
    private var originId: Int = 0

    /*** 当前文章是否被收藏 ***/
    private var collected: Boolean = false

    private val webHelper = WebHelper()

    override fun showContent(): Boolean = true

    /*** 初始化数据 ***/
    override fun initData() {
        this.url = intent.getStringExtra("url").orEmpty()
        this.title = intent.getStringExtra("title").orEmpty()
        this.articleId = intent.getIntExtra("articleId", articleId)
        this.originId = intent.getIntExtra("originId", originId)
        LogUtils.iTag("Web", "url = $url", "title = $title", "articleId = $articleId", "originId = $originId")
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            if (articleId != 0) {
                collected = DataManager.hasCollected(articleId)
                mTitleBar.setRightDrawable(if (collected) R.drawable.ic_collected else R.drawable.ic_collect)
            }
            mTitleBar.setTitle(title)
            webHelper.init(mContext, this.mConainer, R.layout.layout_delegate_web_error, url)
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            // 点击收藏
            mTitleBar.setOnRightClickListener {
                if (collected) {
                    // 取消收藏
                    mLottieView.visible()
                    mLottieView.repeatMode = LottieDrawable.REVERSE
                    mLottieView.playAnimation()
                } else {
                    // 收藏文章
                    mLottieView.visible()
                    mLottieView.repeatMode = LottieDrawable.RESTART
                    mLottieView.playAnimation()
                }
            }
            // 收藏动画结束后
            mLottieView.addAnimatorListener(object : SimpleAnimatorListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    mLottieView.gone()
                    super.onAnimationEnd(animation)
                }
            })
        }
    }

    /*** 页面开始请求 ***/
    override fun start() {
    }

}