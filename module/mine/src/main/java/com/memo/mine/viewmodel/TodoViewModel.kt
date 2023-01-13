package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.local.TodoContent
import com.memo.base.entity.local.TodoFilter
import com.memo.base.entity.local.TodoStatus
import com.memo.base.entity.local.Zip2
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
    val addOrEditLiveData = MutableLiveData<TodoInfo>()
    val deleteLiveData = MutableLiveData<Int>()
    val statusLiveData = MutableLiveData<Zip2<Int, TodoStatus>>()

    /**
     * 获取清单列表
     * @param filter TodoFilter
     */
    fun todoList(filter: TodoFilter) {
        request(ApiRepository.todoList(filter), listLiveData::postValue)
    }

    /**
     * 新增清单
     * @param params TodoContent
     */
    fun todoAdd(params: TodoContent) {
        showLoading()
        request(ApiRepository.todoAdd(params), addOrEditLiveData::postValue)
    }

    /**
     * 修改清单
     * @param params TodoContent
     */
    fun todoEdit(params: TodoContent) {
        showLoading()
        request(ApiRepository.todoEdit(params), addOrEditLiveData::postValue)
    }

    /**
     * 删除清单
     * @param id Int
     */
    fun todoDelete(id: Int) {
        showLoading()
        request(ApiRepository.todoDelete(id)) {
            deleteLiveData.postValue(id)
        }
    }

    /**
     * 修改清单状态
     * @param id Int
     * @param status TodoStatus
     */
    fun todoStatus(id: Int, status: TodoStatus) {
        showLoading()
        request(ApiRepository.todoStatus(id, status)) {
            statusLiveData.postValue(Zip2(id, status))
        }
    }


}