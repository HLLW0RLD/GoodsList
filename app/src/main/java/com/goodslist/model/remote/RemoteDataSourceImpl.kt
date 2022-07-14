package com.goodslist.model.remote

import com.goodslist.model.data.Product
import com.goodslist.model.domain.ProductAPI
import com.goodslist.model.domain.RemoteDataSource
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteDataSourceImpl(private val api : ProductAPI) : RemoteDataSource, KoinComponent{
    override fun getList(): Observable<Product> {
        return api.getList()
    }
}