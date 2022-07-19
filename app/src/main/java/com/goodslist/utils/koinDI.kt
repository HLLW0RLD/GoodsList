package com.goodslist.utils

import com.goodslist.model.domain.ProductAPI
import com.goodslist.model.domain.RemoteDataSource
import com.goodslist.model.domain.Repository
import com.goodslist.model.remote.RemoteDataSourceImpl
import com.goodslist.model.repository.RepositoryImpl
import com.goodslist.view.list.ListViewModel
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{
    val baseUrl = "https://edo.ilexx.ru/test/"

    single<ListViewModel> { ListViewModel() }
    single<Repository> { RepositoryImpl(RemoteDataSourceImpl(get())) }
    single<ProductAPI> { get<Retrofit>().create(ProductAPI::class.java) }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(get())
            .build()
    }
    factory<Converter.Factory> { GsonConverterFactory.create(GsonBuilder().setLenient().create()) }

}