package com.memo.base.common.activity

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.memo.base.R
import com.memo.base.base.BaseVmActivity
import com.memo.base.common.viewmodel.ArticleViewModel
import com.memo.base.databinding.ActivityArticleBinding
import com.memo.base.manager.BusManager
import com.memo.base.manager.DataManager
import com.memo.base.utils.checkLogin
import com.memo.base.utils.web.WebHelper
import com.memo.core.utils.ext.startActivity

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
class ArticleActivity : BaseVmActivity<ArticleViewModel, ActivityArticleBinding>() {

    companion object {
        /**
         * 列表跳转文章
         */
        fun startFromList(context: Context, title: String, url: String, articleId: Int) {
            context.startActivity<ArticleActivity>("title" to title, "url" to url, "articleId" to articleId)
        }

        /**
         * 收藏列表跳转文章
         */
        fun startFromCollect(context: Context, title: String, url: String, articleId: Int, collectId: Int) {
            context.startActivity<ArticleActivity>("title" to title, "url" to url, "articleId" to articleId, "collectId" to collectId)
        }
    }

    /*** 网页标题 ***/
    private var title: String = ""

    /*** 网页地址 ***/
    private var url: String = ""

    /*** 资讯id ***/
    private var articleId: Int = -1

    /*** 收藏的id ***/
    private var collectId: Int = -1

    /*** 当前文章是否被收藏 ***/
    private var isArticleCollected: Boolean = false


    override fun showContent(): Boolean = true

    /*** 初始化数据 ***/
    override fun initData() {
        this.title = intent.getStringExtra("title").orEmpty()
        this.url = intent.getStringExtra("url").orEmpty()
        this.articleId = intent.getIntExtra("articleId", articleId)
        this.collectId = intent.getIntExtra("collectId", collectId)
        LogUtils.iTag("Web", "title = $title", "url = $url", "articleId = $articleId", "collectId = $collectId")
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mTitleBar.setTitle(title)
            isArticleCollected = DataManager.hasCollected(articleId)
            mTitleBar.showRight(articleId != -1)
            mTitleBar.setRightDrawable(if (isArticleCollected) R.drawable.icon_collected else R.drawable.icon_collect)
            WebHelper.init(mContext, this.mContainer, R.layout.layout_delegate_web_error, url)
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mTitleBar.setOnRightClickListener {
                if (checkLogin(true)) {
                    if (isArticleCollected) {
                        // 取消收藏
                        mCollectAnim.startUnCollect()
                        if (collectId == -1) {
                            // 取消收藏不在收藏列表的文章
                            mViewModel.deleteCollectInDetail(articleId)
                        } else {
                            // 取消收藏在收藏泪飙的文章
                            mViewModel.deleteCollectInCollect(articleId, collectId)
                        }
                    } else {
                        // 开启收藏 只允许收藏站内文章
                        mCollectAnim.startCollect()
                        mViewModel.addInnerArticleCollect(articleId)
                    }
                }
            }
        }

        // 收藏和取消收藏的监听
        mViewModel.collectLiveData.observe(this, this::onCollectResponse)

        // 监听用户登录事件
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
        // 更新收藏按钮
        isArticleCollected = collected
        mBinding.mTitleBar.setRightDrawable(if (isArticleCollected) R.drawable.icon_collected else R.drawable.icon_collect)
    }

    /**
     * 用户登录返回
     * @param login Boolean
     */
    private fun onLoginEvent(login: Boolean) {
        isArticleCollected = DataManager.hasCollected(articleId)
        mBinding.mTitleBar.setRightDrawable(if (isArticleCollected) R.drawable.icon_collected else R.drawable.icon_collect)
    }

}