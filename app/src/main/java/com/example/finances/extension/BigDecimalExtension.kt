package com.example.finances.extension

import java.math.BigDecimal
import java.util.Locale

fun BigDecimal.formatCurrency(): String {
    val currencyInstance = java.text.DecimalFormat
            .getCurrencyInstance(Locale("pt", "br"))

    return currencyInstance.format(this).replace("-R$", "R$ -")
}