package com.memo.mine.ui.activity.collect

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ClipboardUtils
import com.memo.base.base.BaseVmActivity
import com.memo.base.entity.remote.WebUrl
import com.memo.base.manager.BusManager
import com.memo.base.utils.finish
import com.memo.base.utils.onItemChildClick
import com.memo.base.utils.showEmpty
import com.memo.base.utils.toast
import com.memo.base.widget.EmptyView
import com.memo.mine.R
import com.memo.mine.databinding.ActivityWebsiteCollectBinding
import com.memo.mine.ui.adapter.WebsiteAdapter
import com.memo.mine.utils.DialogUtils
import com.memo.mine.viewmodel.WebsiteViewModel

/**
 * title:网址收藏
 * describe:
 *
 * @author memo
 * @date 2022-12-15 19:24
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebsiteCollectActivity : BaseVmActivity<WebsiteViewModel, ActivityWebsiteCollectBinding>() {

    private val mAdapter = WebsiteAdapter()

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
        // 显示添加网址收藏
        mBinding.mTitleBar.setOnRightClickListener {
            DialogUtils.showAddWebUrlDialog { name, link ->
                mViewModel.addWebsiteCollect(name, link)
            }
        }
        // 刷新
        mBinding.mRefreshLayout.setOnRefreshListener {
            mViewModel.getWebsiteCollectList()
        }

        mAdapter.onItemChildClick { viewId, data ->
            when (viewId) {
                // 修改
                R.id.mIvEdit -> DialogUtils.showEditWebUrlDialog(data) { id, name, link ->
                    mViewModel.editWebsiteCollect(id, name, link)
                }
                // 删除
                R.id.mIvDelete -> mViewModel.deleteWebsiteCollect(data.id)
                // 点击
                R.id.mItem -> {
                    ClipboardUtils.copyText(data.link)
                    toast("内容已复制到剪切板")
                }
            }
        }
        // 查
        mViewModel.listLiveData.observe(this, this::onListResponse)
        // 增
        mViewModel.addLiveData.observe(this, this::onAddResponse)
        // 改
        mViewModel.editLiveData.observe(this, this::onEditResponse)
        // 删
        mViewModel.deleteLiveData.observe(this, this::onDeleteResponse)
        // 用户登录监听
        BusManager.userLiveData.observe(this, this::onLoginResponse)
    }


    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getWebsiteCollectList()
    }

    /**
     * 获取收藏列表
     * @param data ArrayList<WebSite>
     */
    private fun onListResponse(data: ArrayList<WebUrl>) {
        mAdapter.showEmpty(data.isEmpty(), EmptyView.EMPTY_COLLECT)
        mAdapter.setList(data)
        mBinding.mRefreshLayout.finish(data.isNotEmpty())
    }

    /**
     * 新增收藏
     * @param data Website
     */
    private fun onAddResponse(data: WebUrl) {
        mAdapter.addData(data)
    }

    /**
     * 修改收藏
     * @param data WebSite
     */
    private fun onEditResponse(data: WebUrl) {
        mAdapter.setData(mAdapter.data.indexOfFirst { it.id == data.id }, data)
    }

    /**
     * 删除收藏
     * @param id Int
     */
    private fun onDeleteResponse(id: Int) {
        mAdapter.removeAt(mAdapter.data.indexOfFirst { it.id == id })
        if (mAdapter.data.size == 0) mBinding.mRefreshLayout.autoRefresh()
    }

    /**
     * 监听用户登录
     * @param login Boolean
     */
    private fun onLoginResponse(login: Boolean) {
        this.start()
    }
}