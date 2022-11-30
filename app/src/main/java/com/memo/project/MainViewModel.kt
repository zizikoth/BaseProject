package com.memo.project

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.local.Zip2Null
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import kotlinx.coroutines.flow.combine

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

    val dataLiveData = MutableLiveData<Zip2Null<ArrayList<Article>, ListEntity<Article>>>()

    fun getArticles(page: Int) {
        request(request = { repository.getArticles(page) }, onSuccess = {
            dataLiveData.postValue(Zip2Null(null, it))
        })
    }

    fun getHomeData() {
        showLoading()
        val combine = combine(repository.getBanner(), repository.getArticles(0)) { banner, article ->
            Zip2Null(banner, article)
        }
        request(request = { combine }, onSuccess = {
            dataLiveData.postValue(it)
        })
    }
}