package com.memo.mine.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.memo.business.base.BaseVmFragment
import com.memo.business.entity.local.Zip4
import com.memo.business.manager.DataManager
import com.memo.business.manager.RouteManager
import com.memo.business.utils.checkLogin
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.startActivity
import com.memo.core.utils.ext.visible
import com.memo.mine.databinding.FragmentMineBinding
import com.memo.mine.ui.activity.coin.CoinActivity
import com.memo.mine.ui.activity.collect.InnerCollectActivity
import com.memo.mine.ui.activity.collect.OuterCollectActivity
import com.memo.mine.ui.activity.rank.RankActivity
import com.memo.mine.ui.activity.share.ShareActivity
import com.memo.mine.ui.activity.square.SquareActivity
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
        mBinding.run {
            root.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mLlCollect.onClick {
                if (checkLogin()) startActivity<InnerCollectActivity>()
            }
            mLlCoin.onClick {
                if (checkLogin()) startActivity<CoinActivity>()
            }
            mLlRank.onClick {
                if (checkLogin()) startActivity<RankActivity>()
            }
            mItemTodo.onClick {
                if (checkLogin()) RouteManager.startTodoActivity()
            }
            mItemCollect.onClick {
                if (checkLogin()) startActivity<OuterCollectActivity>()
            }
            mItemShare.onClick {
                if (checkLogin()) ShareActivity.start(mActivity,DataManager.getUser()?.id?:0)
            }
            mItemSquare.onClick {
                startActivity<SquareActivity>()
            }
        }

        mViewModel.infoLiveData.observe(this, this::onInfoResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getMineInfo()
    }

    /**
     * 获取用户的数量信息
     * @param data Zip4<Int, Int, Int, Int>
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

}