package com.memo.main.ui.fragment.account

import com.memo.base.base.BaseFragment
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.value
import com.memo.main.databinding.FragmentLoginBinding
import com.memo.main.ui.activity.account.AccountActivity
import kotlinx.coroutines.FlowPreview

/**
 * title:登录模组
 * describe:
 *
 * @author memo
 * @date 2022-12-07 14:54
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@FlowPreview
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun initialize() {
        mBinding.run {
            mEtUserName.value = "Mr.Memo"
            mEtPassword.value = "zhx931024"
            mBtnLogin.onClick {
                if (mActivity is AccountActivity) {
                    (mActivity as AccountActivity).login(mEtUserName.value, mEtPassword.value)
                }
            }
        }
    }
}