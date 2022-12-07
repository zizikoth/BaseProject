package com.memo.main.ui.account

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.memo.business.base.BaseVmActivity
import com.memo.core.utils.extra.onClick
import com.memo.core.utils.extra.value
import com.memo.main.R
import com.memo.main.databinding.ActivityAccountBinding

class AccountActivity : BaseVmActivity<AccountViewModel, ActivityAccountBinding>() {

    private val mLoginDialog by lazy {
        CustomDialog.build().setAlign(CustomDialog.ALIGN.BOTTOM).setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_login) {
                override fun onBind(dialog: CustomDialog, parent: View) {
                    val btn = parent.findViewById<TextView>(R.id.mBtnLogin)
                    val mEtAccount = parent.findViewById<EditText>(R.id.mEtAccount)
                    val mEtPassword = parent.findViewById<EditText>(R.id.mEtPassword)
                    btn.onClick {
                        mViewModel.login(mEtAccount.value, mEtPassword.value)
                    }
                }
            })

    }

    override fun showContent(): Boolean = true
    override fun transparentStatusBar(): Boolean = true

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initListener() {
        mBinding.mBtnSignIn.onClick { mLoginDialog.show() }
    }

    override fun start() {
    }

}