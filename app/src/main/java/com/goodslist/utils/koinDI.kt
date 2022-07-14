package com.goodslist.utils

import org.koin.dsl.module

val appModule = module{

    single<String> { "https://edo.ilexx.ru/test/" }
}