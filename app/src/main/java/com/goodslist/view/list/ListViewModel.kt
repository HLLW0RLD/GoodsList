package com.goodslist.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodslist.model.domain.Repository
import com.goodslist.utils.AppState
import com.goodslist.utils.converterToList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListViewModel : ViewModel(), KoinComponent {
    private val repository : Repository by inject()
    val data : MutableLiveData<AppState> = MutableLiveData()
    private val composite : CompositeDisposable = CompositeDisposable()

    fun getProductsList(){
        data.value = AppState.Loading
        composite.add(
            repository
                .getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        val list = converterToList(it)
                        data.postValue(
                            AppState.Success(list)
                        )
                    },
                    onError = {
                        data.postValue(
                            AppState.Error("Ошибка соединения")
                        )
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}