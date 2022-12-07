package com.memo.main.ui.fragment.account

import com.memo.business.base.BaseFragment
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.value
import com.memo.main.databinding.FragmentRegisterBinding
import com.memo.main.ui.activity.account.AccountActivity

/**
 * title:注册模组
 * describe:
 *
 * @author memo
 * @date 2022-12-07 14:55
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override fun initialize() {
        mBinding.run {
            mBtnRegister.onClick {
                if (mActivity is AccountActivity) {
                    (mActivity as AccountActivity).register(mEtUserName.value, mEtPassword.value, mEtRePassword.value)
                }
            }
        }
    }
}