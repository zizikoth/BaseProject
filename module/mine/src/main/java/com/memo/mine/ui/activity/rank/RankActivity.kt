package com.memo.mine.ui.activity.rank

import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.RankRecord
import com.memo.base.manager.BusManager
import com.memo.base.utils.finish
import com.memo.base.utils.showEmpty
import com.memo.mine.databinding.ActivityRankBinding
import com.memo.mine.ui.adapter.RankAdapter
import com.memo.mine.viewmodel.MineViewModel
import com.memo.mine.viewmodel.RankViewModel

/**
 * title:排名界面
 * describe:
 *
 * @author memo
 * @date 2022-12-14 19:51
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class RankActivity : BaseVmActivity<RankViewModel, ActivityRankBinding>() {

    /*** 页码 ***/
    private var pageNum: Int = 1

    private val mAdapter = RankAdapter()

    /*** 初始化数据 ***/
    override fun initData() {}

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
            mViewModel.getRankList(pageNum)
        }
        // 加载
        mBinding.mRefreshLayout.setOnLoadMoreListener {
            mViewModel.getRankList(pageNum)
        }
        // 查
        mViewModel.listLiveData.observe(this, this::onRankResponse)
        // 用户登录监听
        BusManager.userLiveData.observe(this, this::onLoginResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getRankList(pageNum)
    }

    /**
     * 获取排名数据返回
     * @param data ListEntity<RankRecord>
     */
    private fun onRankResponse(data: ListEntity<RankRecord>) {
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