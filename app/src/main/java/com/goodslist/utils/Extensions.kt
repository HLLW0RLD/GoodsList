package com.goodslist.utils

import android.view.View
import com.goodslist.model.data.Product
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.stream.Collectors
//Порядковый номер атрибутов после удаления лишнего и индекс с которого начинается следующий элемент
private const val NEXT_PRODUCT = 20
private var ATTR_UNKNOWN = 18
private var ATTR_CODE = 0
private var ATTR_NAME = 1
private var ATTR_PRICE = 3
private var ATTR_REMAINDER = 4
private var ATTR_TYPE = 18
private var ATTR_ALCOHOL = 19
//Строки которые необходимо удалить
private const val FIRST_SPLIT = "<goods_attr id="
private const val LAST_SPLIT = "</goods_attr>"
private const val USELESS_ATTR23 = "attr_id=\"23"
private const val USELESS_ATTR24 = "attr_id=\"24"
private const val USELESS_ATTR25 = "attr_id=\"25"
private const val USELESS_ATTR26 = "attr_id=\"26"
private const val USELESS_ATTR30 = "attr_id=\"30"
private const val USELESS_ATTR31 = "attr_id=\"31"
private const val USELESS_ATTR32 = "attr_id=\"32"

fun View.showSnackBar(text: String, actionText: String, action: (View) -> Unit) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action).show()
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun converterToList(response: ResponseBody): List<Product> {
    val iS: InputStream = response.byteStream()
    var product: Product
    val list: MutableList<Product> = mutableListOf()
    val br = BufferedReader(InputStreamReader(iS, Charsets.UTF_8))
    val line: String = br.lines().collect(Collectors.joining());
    val attr: MutableList<String> = line.split(Regex(";|$FIRST_SPLIT|$LAST_SPLIT")) as MutableList<String>
    while (true) {
        if (ATTR_CODE >= attr.size ||
            ATTR_NAME >= attr.size ||
            ATTR_REMAINDER >= attr.size ||
            ATTR_PRICE >= attr.size ||
            ATTR_TYPE >= attr.size ||
            ATTR_ALCOHOL >= attr.size
        ) {
            break
        }
        //Удаление лишний атрибутов
        var i = 0
        while (i <= attr.size - 1) {
            if (attr[i].contains(USELESS_ATTR23) ||
                attr[i].contains(USELESS_ATTR24) ||
                attr[i].contains(USELESS_ATTR25) ||
                attr[i].contains(USELESS_ATTR26) ||
                attr[i].contains(USELESS_ATTR30) ||
                attr[i].contains(USELESS_ATTR31) ||
                attr[i].contains(USELESS_ATTR32)
                && i < (attr.size - 1)
            ) {
                attr.removeAt(i)
            } else {
                if (i < attr.size) {
                    i++
                }
            }
        }
        //Удаление пустых строк и штрихкодов
        var j = 0
        while (j <= attr.size - 1) {
            if (attr[j] == "" || attr[j].length == 13) {
                attr.removeAt(j)
            } else {
                if (j < attr.size) {
                    j++
                }
            }
        }
        //Проверка и удаление лишнего, именно здесь возникла ошибка
        // т.к код следующего элемента сливается с этим лишним атрибутом
        // из-за этого список в нескольких местах отображается не корректно
        while (ATTR_UNKNOWN <= attr.size - 1) {
            if (attr[ATTR_UNKNOWN].length > 1) {
                val listOfChar = attr[ATTR_UNKNOWN].toCharArray()
                listOfChar.drop(1)
                val newString = String(listOfChar)
                attr.set(ATTR_UNKNOWN, newString)
            } else {
                attr.removeAt(ATTR_UNKNOWN)
            }
            ATTR_UNKNOWN += NEXT_PRODUCT
        }
        product = Product(
            code = attr[ATTR_CODE],
            name = attr[ATTR_NAME],
            remainder = attr[ATTR_REMAINDER],
            price = attr[ATTR_PRICE] + " P",
            retail = attr[ATTR_REMAINDER] + " * " + attr[ATTR_PRICE],
            type = typeConverter(attr[ATTR_TYPE], attr),
            alcohol = alcoConverter(attr[ATTR_ALCOHOL], attr)
        )
        ATTR_CODE += NEXT_PRODUCT
        ATTR_NAME += NEXT_PRODUCT
        ATTR_REMAINDER += NEXT_PRODUCT
        ATTR_PRICE += NEXT_PRODUCT
        ATTR_TYPE += NEXT_PRODUCT
        ATTR_ALCOHOL += NEXT_PRODUCT

        list.add(product)
    }
    return list
}
//Заполнение типа продукта
fun typeConverter(code: String, list: MutableList<String>): String {
    var type = " - "
    if (code.contains("200")) {
        type = "водка"
        return type
    }
    if (code.contains("229")) {
        type = "коньяк"
        return type
    }
    if (code.contains("212")) {
        type = "настойка горькая"
        return type
    }
    if (code.contains("450")) {
        type = "шампанское"
        return type
    }
    if (code.contains("461")) {
        type = "напиток винный"
        return type
    }
    if (code.contains("462")) {
        type = "напиток винный"
        return type
    }
    if (code.contains("421")) {
        type = "вино"
        return type
    }
    if (code.contains("211")) {
        type = "настойка сладкая"
        return type
    }
    if (code.contains("403")) {
        type = "вино столовое"
        return type
    }
    if (code.contains("280")) {
        type = "напиток спиртной крепкий"
        return type
    }
    if (code.contains("500")) {
        type = "пиво"
        return type
    }
    if (code.contains("520")) {
        type = "пивной напиток"
        return type
    } else {
        list.add(ATTR_TYPE, type)
    }
    return type
}
//Заполнение атрибута алкоголь
fun alcoConverter(line: String, list: MutableList<String>): String {
    val code = list[ATTR_CODE]
    var newLine = " - "
    if (line.contains("27")) {
        val newValue = ""
        newLine = line.replace("\"$code\" attr_id=\"27\">", newValue)
        return newLine
    } else {
        list.add(ATTR_ALCOHOL, newLine)
    }
    return newLine
}


