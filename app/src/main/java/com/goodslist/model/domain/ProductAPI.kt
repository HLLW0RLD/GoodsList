package com.goodslist.model.domain

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ProductAPI {
    @GET("catalog.spr")
    fun getList() : Observable<ResponseBody>
}