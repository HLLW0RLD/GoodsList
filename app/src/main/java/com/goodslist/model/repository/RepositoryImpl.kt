package com.goodslist.model.repository

import com.goodslist.model.data.Product
import com.goodslist.model.domain.RemoteDataSource
import com.goodslist.model.domain.Repository
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(private val remote: RemoteDataSource) : Repository {
    override fun getList(): Observable<Product> {
        return remote.getList()
    }
}