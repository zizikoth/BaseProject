package com.memo.mine.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.memo.base.base.BaseVmFragment
import com.memo.base.entity.local.Zip4
import com.memo.base.manager.BusManager
import com.memo.base.manager.DataManager
import com.memo.base.manager.RouteManager
import com.memo.base.utils.checkLogin
import com.memo.core.utils.ext.*
import com.memo.mine.databinding.FragmentMineBinding
import com.memo.mine.ui.activity.coin.CoinActivity
import com.memo.mine.ui.activity.collect.ArticleCollectActivity
import com.memo.mine.ui.activity.collect.WebsiteCollectActivity
import com.memo.mine.ui.activity.notify.NotifyActivity
import com.memo.mine.ui.activity.rank.RankActivity
import com.memo.mine.ui.activity.setting.SettingActivity
import com.memo.mine.ui.activity.share.ShareActivity
import com.memo.mine.ui.activity.square.SquareActivity
import com.memo.mine.ui.activity.todo.TodoActivity
import com.memo.mine.viewmodel.MineViewModel

/**
 * title:我的
 * describe:
 *
 * @author memo
 * @date 2022-12-12 16:30
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Route(path = RouteManager.MineFragment)
class MineFragment : BaseVmFragment<MineViewModel, FragmentMineBinding>() {

    override fun showContent() = true

    /*** 初始化数据 ***/
    override fun initData() {
        mBinding.mTvName.text = DataManager.getUser()?.nickname ?: "请登录"
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.root.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            // 设置
            mIvSetting.onClick {
                startActivity<SettingActivity>()
            }
            // 通知
            mIvNotify.onClick {
                if (checkLogin()) startActivity<NotifyActivity>()
            }
            // 收藏
            mLlCollect.onClick {
                if (checkLogin()) startActivity<ArticleCollectActivity>()
            }
            // 积分
            mLlCoin.onClick {
                if (checkLogin()) startActivity<CoinActivity>()
            }
            // 排名
            mLlRank.onClick {
                if (checkLogin()) startActivity<RankActivity>()
            }
            // 待办清单
            mItemTodo.onClick {
                if (checkLogin()) startActivity<TodoActivity>()
            }
            // 我的收藏
            mItemCollect.onClick {
                if (checkLogin()) startActivity<WebsiteCollectActivity>()
            }
            // 我的分享
            mItemShare.onClick {
                if (checkLogin()) ShareActivity.start(mActivity, DataManager.getUser()?.id ?: 0)
            }
            // 分享广场
            mItemSquare.onClick {
                startActivity<SquareActivity>()
            }
        }
        // 消息通知
        mViewModel.dotLiveData.observe(this, this::onNotifyResponse)
        // 用户展示信息
        mViewModel.infoLiveData.observe(this, this::onInfoResponse)
        // 收藏数据更新
        BusManager.collectLiveData.observe(this, this::onCollectEvent)
        // 用户登录通知
        BusManager.userLiveData.observe(this, this::onLoginEvent)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getMineInfo()
    }

    /*** 判断当前界面是否处于可见状态 ***/
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        // 每当页面可见并且准备好了 获取一次未读消息
        if (!hidden && isPrepared) mViewModel.unReadMessageCount()
    }

    /**
     * 未读通知数据返回
     * @param count Int 数量
     */
    private fun onNotifyResponse(count: Int) {
        mBinding.mTvNotify.run {
            val width = if (count > 9) dimen(com.memo.core.R.dimen.dp24) else dimen(com.memo.core.R.dimen.dp14)
            widthAndHeight(width.toInt(), layoutParams.height)
            text = if (count > 99) "99+" else count.toString()
            setVisible(count > 0)
        }
    }

    /**
     * 获取用户的数量信息
     * @param data 等级，收藏，积分，排名
     */
    private fun onInfoResponse(data: Zip4<Int, Int, Int, Int>) {
        mBinding.run {
            mLlLevel.visible()
            mTvLevel.text = data.first.toString()
            mTvCollect.text = data.second.toString()
            mTvCoin.text = data.third.toString()
            mTvRank.text = data.forth.toString()
        }
    }

    /**
     * 通知：更新收藏数据
     * @param refresh Boolean
     */
    private fun onCollectEvent(refresh: Boolean) {
        mViewModel.getMineInfo()
    }

    /**
     * 通知：更新用户信息
     * @param login Boolean
     */
    private fun onLoginEvent(login: Boolean) {
        if (login) {
            mViewModel.getMineInfo()
        } else {
            mBinding.run {
                mTvName.text = "请登录"
                mLlLevel.gone()
                mTvCollect.text = "--"
                mTvCoin.text = "--"
                mTvRank.text = "--"
            }
        }
    }

}