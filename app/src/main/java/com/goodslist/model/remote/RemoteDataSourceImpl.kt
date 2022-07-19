package com.goodslist.model.remote

import com.goodslist.model.domain.ProductAPI
import com.goodslist.model.domain.RemoteDataSource
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

class RemoteDataSourceImpl(private val api : ProductAPI) : RemoteDataSource{
    override fun getList(): Observable<ResponseBody> {
        return api.getList()
    }
}