package com.goodslist.model.repository

import com.goodslist.model.domain.RemoteDataSource
import com.goodslist.model.domain.Repository
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

class RepositoryImpl(private val remote: RemoteDataSource) : Repository{
    override fun getList(): Observable<ResponseBody> {
        return remote.getList()
    }
}