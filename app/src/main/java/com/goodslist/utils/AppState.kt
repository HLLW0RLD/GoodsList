package com.goodslist.utils

import com.goodslist.model.data.Product

sealed class AppState {
    data class Success(val products : List<Product>) : AppState()
    object Loading : AppState()
    class Error(msg : String) : AppState()
}