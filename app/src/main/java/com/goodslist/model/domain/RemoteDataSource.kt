package com.goodslist.model.domain

import com.goodslist.model.data.Product
import io.reactivex.rxjava3.core.Observable

interface RemoteDataSource {
    fun getList() : Observable<Product>
}