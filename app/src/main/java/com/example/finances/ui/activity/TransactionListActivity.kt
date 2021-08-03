package com.example.finances.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finances.R
import com.example.finances.model.Transaction
import com.example.finances.model.Types
import com.example.finances.ui.adapter.TransactionsListAdapter
import kotlinx.android.synthetic.main.activity_transaction_list.*
import java.math.BigDecimal
import java.util.*

//TODO trocar synthetic por view binding
//TODO corrigir e otimizar layout da activity
//TODO aplicar novos estilos ao layout da activity e demais layouts
//TODO alterar cores de despesa e receita
//TODO alterar id e nome de imagens pt para inglês

class TransactionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        val transactionsList: List<Transaction> = transactionListExamples()

        createListTransaction(transactionsList)
    }

    private fun createListTransaction(transactionsList: List<Transaction>) {
        transactions_listview.adapter = TransactionsListAdapter(transactionsList, this)
    }

    private fun transactionListExamples(): List<Transaction> = listOf(
        Transaction(
            date = Calendar.getInstance(),
            value = BigDecimal(10),
            type = Types.EXPENSE
        ),
        Transaction(
            value = BigDecimal(20.5),
            category = "Comida",
            type = Types.EXPENSE
        ),
        Transaction(
            type = Types.REVENUE,
            value = BigDecimal(100),
            category = "Economia"
        )
    )
}