package com.goodslist.model.domain

import com.goodslist.model.data.Product
import io.reactivex.rxjava3.core.Observable

interface Repository {
    fun getList() : Observable<Product>
}