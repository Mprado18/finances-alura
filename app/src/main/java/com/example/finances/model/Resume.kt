package com.example.finances.model

import java.math.BigDecimal

class Resume(private val transactionsList: List<Transaction>) {

    fun revenue(): BigDecimal = sumType(Types.REVENUE)

    fun expense(): BigDecimal = sumType(Types.EXPENSE)

    private fun sumType(type: Types): BigDecimal {
        val sumTransactionsByType = transactionsList
                .filter { it.type == type }
                .sumByDouble { it.value.toDouble() }
        return BigDecimal(sumTransactionsByType)
    }

    fun total(): BigDecimal = revenue().subtract(expense())

}