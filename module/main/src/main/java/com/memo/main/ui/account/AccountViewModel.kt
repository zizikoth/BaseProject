package com.memo.main.ui.account

import com.memo.business.base.BaseViewModel
import com.memo.business.manager.UserManager

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-06-03 13:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class AccountViewModel : BaseViewModel() {
    private val repository = AccountRepository()

    fun login(username: String, password: String) {
        request(
            request = { repository.login(username, password) },
            onSuccess = {
                UserManager.setUser(it)
            }
        )

    }
}