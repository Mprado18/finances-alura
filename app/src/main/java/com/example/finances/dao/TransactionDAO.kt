package com.example.finances.dao

import com.example.finances.model.Transaction

class TransactionDAO {

    val transactions: List<Transaction> = Companion.transactions
    companion object {
        val transactions: MutableList<Transaction> = mutableListOf()
    }

    fun addTransaction(transaction: Transaction) {
        Companion.transactions.add(transaction)
    }

    fun confirmAlterationOfTransaction(transaction: Transaction, position: Int) {
        Companion.transactions[position] = transaction
    }

    fun removeTransaction(position: Int) {
        Companion.transactions.removeAt(position)
    }

}