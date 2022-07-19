package com.goodslist.model.domain

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

interface RemoteDataSource {
    fun getList() : Observable<ResponseBody>
}