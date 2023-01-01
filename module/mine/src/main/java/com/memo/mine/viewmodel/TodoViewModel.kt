package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.local.TodoFilter
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.TodoInfo

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-13 11:36
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class TodoViewModel : BaseViewModel() {

    val listLiveData = MutableLiveData<ListEntity<TodoInfo>>()

    fun todoList(filter: TodoFilter) {
        request(ApiRepository.todoList(filter), listLiveData::postValue)
    }
}