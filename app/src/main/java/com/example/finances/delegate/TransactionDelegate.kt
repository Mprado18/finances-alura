package com.example.finances.delegate

import com.example.finances.model.Transaction

interface TransactionDelegate {

    fun delegate(transaction: Transaction)
}