package com.memo.base.common.activity

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.memo.base.R
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.viewmodel.ArticleViewModel
import com.memo.base.databinding.ActivityWebBinding
import com.memo.base.manager.BusManager
import com.memo.base.manager.DataManager
import com.memo.base.utils.checkLogin
import com.memo.core.utils.ext.startActivity
import com.memo.base.utils.web.WebHelper

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
        /**
         * 通用跳转网页
         */
        fun start(context: Context, url: String, title: String) {
            context.startActivity<WebActivity>("url" to url, "title" to title)
        }

        /**
         * 列表跳转文章
         */
        fun startFromList(context: Context, url: String, articleId: Int, title: String) {
            context.startActivity<WebActivity>("url" to url, "articleId" to articleId, "title" to title)
        }

        /**
         * 收藏列表跳转文章
         */
        fun startFromCollect(context: Context, url: String, collectId: Int, articleId: Int, title: String) {
            context.startActivity<WebActivity>("url" to url, "collectId" to collectId, "articleId" to articleId, "title" to title)
        }
    }

    /*** 网页地址 ***/
    private var url: String = ""

    /*** 网页标题 ***/
    private var title: String = ""

    /*** 资讯id ***/
    private var articleId: Int = 0

    /*** 资讯初始id ***/
    private var collectId: Int = 0

    /*** 当前文章是否被收藏 ***/
    private var isArticleCollected: Boolean = false


    override fun showContent(): Boolean = true

    /*** 初始化数据 ***/
    override fun initData() {
        this.url = intent.getStringExtra("url").orEmpty()
        this.title = intent.getStringExtra("title").orEmpty()
        this.articleId = intent.getIntExtra("articleId", articleId)
        this.collectId = intent.getIntExtra("collectId", collectId)
        LogUtils.iTag("Web", "url = $url", "title = $title", "articleId = $articleId", "collectId = $collectId")
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mTitleBar.setTitle(title)
            // 判断是文章还是网页
            if (articleId != 0 || collectId != 0) {
                isArticleCollected = DataManager.hasCollected(articleId)
                // 站外文章 && 为收藏 不显示收藏按钮
                if (articleId != -1 || isArticleCollected) {
                    mTitleBar.setRightDrawable(if (isArticleCollected) R.drawable.ic_collected else R.drawable.ic_collect)
                }
            }
            WebHelper.init(mContext, this.mConainer, R.layout.layout_delegate_web_error, url)
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mTitleBar.setOnRightClickListener {
                // 1、判断是否登录
                if (checkLogin(true)) {
                    // 2、判断是否已经收藏
                    if (isArticleCollected) {
                        // 取消收藏
                        mCollectAnim.startUnCollect()
                        // 3、判断是站内文章还是站外文章
                        if (articleId == -1) {
                            // 站外文章
                            mViewModel.deleteOuterCollect(articleId, collectId)
                        } else {
                            // 站内文章
                            mViewModel.deleteInnerCollect(articleId)
                        }
                    } else {
                        // 开启收藏 只允许站内文章收藏
                        mCollectAnim.startCollect()
                        mViewModel.addCollect(articleId)
                    }
                }
            }
        }

        mViewModel.collectLiveData.observe(this, this::onCollectResponse)

        BusManager.userLiveData.observe(this, this::onLoginEvent)
    }

    /*** 页面开始请求 ***/
    override fun start() {}

    /**
     * 判断文章是否收藏
     * @param collected Boolean
     */
    private fun onCollectResponse(collected: Boolean) {
        // 刷新收藏列表数据
        BusManager.collectLiveData.post(true)
        // 处理缓存收藏数据
        if (collected) DataManager.addCollected(articleId) else DataManager.removeCollected(articleId)
        // 如果是站外文章取消收藏后 按钮不再显示
        if (!collected && articleId == -1) mBinding.mTitleBar.showRight(false)
        // 更新收藏按钮a
        isArticleCollected = collected
        mBinding.mTitleBar.setRightDrawable(if (isArticleCollected) R.drawable.ic_collected else R.drawable.ic_collect)
    }

    /**
     * 用户登录返回
     * @param login Boolean
     */
    private fun onLoginEvent(login: Boolean) {
        isArticleCollected = DataManager.hasCollected(articleId)
        mBinding.mTitleBar.setRightDrawable(if (isArticleCollected) R.drawable.ic_collected else R.drawable.ic_collect)
    }

}