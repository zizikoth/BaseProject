package com.memo.mine.ui.activity.coin

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.remote.CoinRecord
import com.memo.base.entity.remote.ListEntity
import com.memo.base.manager.BusManager
import com.memo.base.utils.finish
import com.memo.base.utils.showEmpty
import com.memo.mine.databinding.ActivityCoinBinding
import com.memo.mine.ui.adapter.CoinAdapter
import com.memo.mine.viewmodel.CoinViewModel
import com.memo.mine.viewmodel.MineViewModel

/**
 * title:积分获取记录
 * describe:
 *
 * @author memo
 * @date 2022-12-14 19:42
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CoinActivity : BaseVmActivity<CoinViewModel, ActivityCoinBinding>() {

    /*** 页码 ***/
    private var pageNum: Int = 1

    private val mAdapter = CoinAdapter()

    /*** 初始化数据 ***/
    override fun initData() {
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.mRvList.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        // 刷新
        mBinding.mRefreshLayout.setOnRefreshListener {
            pageNum = 1
            mViewModel.getCoinList(pageNum)
        }
        // 加载
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getCoinList(pageNum)
        }

        // 获取积分信息监听
        mViewModel.listLiveData.observe(this, this::onCoinResponse)
        // 用户登录监听
        BusManager.userLiveData.observe(this, this::onLoginResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getCoinList(pageNum)
    }

    /**
     * 积分信息返回
     * @param data ListEntity<CoinRecord>
     */
    private fun onCoinResponse(data: ListEntity<CoinRecord>) {
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage + 1
        mBinding.mRefreshLayout.finish(data.hasMore())
    }

    /**
     * 监听用户登录
     * @param login Boolean
     */
    private fun onLoginResponse(login: Boolean) {
        this.start()
    }

}