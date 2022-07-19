package com.goodslist.model.domain

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

interface Repository {
    fun getList() : Observable<ResponseBody>
}