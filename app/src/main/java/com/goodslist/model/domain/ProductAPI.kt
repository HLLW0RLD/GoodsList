package com.goodslist.model.domain

import com.goodslist.model.data.Product
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ProductAPI {
    @GET("catalog.spr")
    fun getList() : Observable<Product>
}