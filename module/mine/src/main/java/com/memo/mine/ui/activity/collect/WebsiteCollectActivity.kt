package com.memo.mine.ui.activity.collect

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ClipboardUtils
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.memo.business.base.BaseVmActivity
import com.memo.business.entity.remote.WebSite
import com.memo.business.utils.finish
import com.memo.business.utils.onItemChildClick
import com.memo.business.utils.showEmpty
import com.memo.business.utils.toast
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.value
import com.memo.mine.R
import com.memo.mine.databinding.ActivityWebsiteCollectBinding
import com.memo.mine.ui.adapter.WebsiteAdapter
import com.memo.mine.viewmodel.CollectViewModel

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
class WebsiteCollectActivity : BaseVmActivity<CollectViewModel, ActivityWebsiteCollectBinding>() {

    private val mAdapter = WebsiteAdapter()

    /*** 初始化数据 ***/
    override fun initData() {}

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mRvList.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.mTitleBar.setOnRightClickListener {
            showWebSiteDialog(null)
        }
        mBinding.mRefreshLayout.setOnRefreshListener {
            mViewModel.getWebSiteCollectList()
        }

        mAdapter.onItemChildClick { viewId, data ->
            when (viewId) {
                R.id.mIvEdit -> showWebSiteDialog(data)
                R.id.mIvDelete -> mViewModel.deleteWebSiteCollect(data.id)
                R.id.mItem -> {
                    ClipboardUtils.copyText(data.link)
                    toast("网址已复制到剪切板")
                }
            }
        }

        mViewModel.websiteListLiveData.observe(this, this::onListResponse)
        mViewModel.editLiveData.observe(this, this::onEditResponse)
        mViewModel.deleteLiveData.observe(this, this::onDeleteResponse)
    }


    /*** 页面开始请求 ***/
    override fun start() {
        mViewModel.getWebSiteCollectList()
    }

    /**
     * 显示网址弹窗
     * @param data WebSite?
     */
    private fun showWebSiteDialog(data: WebSite?) {
        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.layout_dialog_website_collect) {
            override fun onBind(dialog: FullScreenDialog, v: View) {
                val mEtName = v.findViewById<EditText>(R.id.mTvName)
                val mEtLink = v.findViewById<EditText>(R.id.mTvLink)
                data?.let {
                    mEtName.value = it.name
                    mEtLink.value = it.link
                }
                v.findViewById<View>(R.id.mBtnSubmit).onClick {
                    val name = mEtName.value
                    val link = mEtLink.value
                    if(name.isEmpty()){
                        toast("请输入收藏网址名称")
                    }else if(link.isEmpty()){
                        toast("请输入收藏网址地址")
                    }else{
                        if (data == null) {
                            // 新增
                            mViewModel.addWebSiteCollect(mEtName.value, mEtLink.value)
                        } else {
                            // 修改
                            mViewModel.editWebSiteCollect(data.id, mEtName.value, mEtLink.value)
                        }
                        dialog.dismiss()
                    }

                }
            }
        })
    }

    /**
     * 获取收藏列表
     * @param data ArrayList<WebSite>
     */
    private fun onListResponse(data: ArrayList<WebSite>) {
        mAdapter.showEmpty(data.isEmpty())
        mAdapter.setList(data)
        mBinding.mRefreshLayout.finish(data.isNotEmpty())
    }

    /**
     * 新增 修改 收藏
     * @param data WebSite
     */
    private fun onEditResponse(data: WebSite) {
        val index = mAdapter.data.indexOfFirst { it.id == data.id }
        if (index == -1) {
            // 新增数据
            mAdapter.addData(data)
        } else {
            // 更新数据
            mAdapter.setData(index, data)
        }
    }

    /**
     * 删除收藏
     * @param id Int
     */
    private fun onDeleteResponse(id: Int) {
        mAdapter.removeAt(mAdapter.data.indexOfFirst { it.id == id })
    }


}