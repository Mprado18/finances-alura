package com.example.finances.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.finances.R
import com.example.finances.delegate.TransactionDelegate
import com.example.finances.model.Transaction
import com.example.finances.model.Types
import com.example.finances.ui.ResumeView
import com.example.finances.ui.adapter.TransactionsListAdapter
import com.example.finances.ui.dialog.CreateTransactionDialog
import kotlinx.android.synthetic.main.activity_transaction_list.*

//TODO trocar synthetic por view binding
//TODO corrigir e otimizar layout da activity
//TODO aplicar novos estilos ao layout da activity e demais layouts
//TODO alterar cores de despesa e receita
//TODO alterar id e nome de imagens pt para inglês

class TransactionListActivity : AppCompatActivity() {

    private val transactions: MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        createResumeCardView()
        createListTransaction()
        handleFloatingActionButton()
    }

    private fun handleFloatingActionButton() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            renderDialog(Types.REVENUE)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            renderDialog(Types.EXPENSE)
        }
    }

    private fun renderDialog(type: Types) {
        CreateTransactionDialog(window.decorView as ViewGroup, this)
            .createDialog(type, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    updateTransactions(transaction)
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }

    private fun updateTransactions(transaction: Transaction) {
        transactions.add(transaction)
        createListTransaction()
        createResumeCardView()
    }

    private fun createResumeCardView() {
        val view = window.decorView
        val resumeView = ResumeView(this, view, transactions)
        resumeView.updateResume()
    }

    private fun createListTransaction() {
        transactions_listview.adapter = TransactionsListAdapter(transactions, this)
    }
}