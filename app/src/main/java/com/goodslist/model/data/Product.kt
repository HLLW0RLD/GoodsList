package com.goodslist.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val code: String = "0000",
    val name: String = "0000",
    val remainder: String = "00.00",
    val price: String = "00.00",
    val retail: String = "00.00",
    val type: String = "0000",
    val alcohol: String = "00.00"
) : Parcelable