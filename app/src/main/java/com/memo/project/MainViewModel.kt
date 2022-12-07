package com.memo.project

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.local.Zip2
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 14:25
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MainViewModel : BaseViewModel() {

    private val repository = MainRepository()

    val dataLiveData = MutableLiveData<String>()

    /**
     * 单次请求
     */
    fun getOnceRequest() {
        showLoading()
        request(
            request = { repository.getArticles(0) },
            onSuccess = { dataLiveData.postValue("单次请求\n文章标题:" + it.datas.first().title) },
            onError = { dataLiveData.postValue("单次请求失败:$it") })
    }

    /**
     * 串行多次请求
     */
    fun getConcatRequest() {
        showLoading()
        val concat = repository.getBanner().flatMapConcat {
            repository.getArticles(it.size)
        }
        request(
            request = { concat },
            onSuccess = { dataLiveData.postValue("串行请求\n文章标题:${it.datas.first().title}") },
            onError = { dataLiveData.postValue("串行请求失败:$it") })
    }

    /**
     * 并行多次请求
     */
    fun getCombineRequest() {
        showLoading()
        val combine = combine(repository.getBanner(), repository.getArticles(0)) { banner, article ->
            Zip2(banner, article)
        }
        request(
            request = { combine },
            onSuccess = {
                dataLiveData.postValue("并行请求\n轮播图标题:${it.first.first().title}\n文章标题:${it.second.datas.first().title}")
            },
            onError = { dataLiveData.postValue("并行请求失败:$it") })
    }
}