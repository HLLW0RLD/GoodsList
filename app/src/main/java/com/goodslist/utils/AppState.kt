package com.goodslist.utils

import com.goodslist.model.data.Product

sealed class AppState {
    class Success(products : List<Product>) : AppState()
    object Loading : AppState()
    class Error(msg : String) : AppState()
}