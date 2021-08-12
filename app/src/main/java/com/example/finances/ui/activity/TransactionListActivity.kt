package com.example.finances.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.finances.R
import com.example.finances.dao.TransactionDAO
import com.example.finances.model.Transaction
import com.example.finances.model.Types
import com.example.finances.ui.ResumeView
import com.example.finances.ui.adapter.TransactionsListAdapter
import com.example.finances.ui.dialog.ChangeTransactionDialog
import com.example.finances.ui.dialog.CreateTransactionDialog
import kotlinx.android.synthetic.main.activity_transaction_list.*

//TODO trocar synthetic por view binding
//TODO corrigir e otimizar layout da activity
//TODO aplicar novos estilos ao layout da activity e demais layouts
//TODO alterar cores de despesa e receita
//TODO alterar id e nome de imagens pt para inglês

class TransactionListActivity : AppCompatActivity() {

    private val dao = TransactionDAO()
    private val transactions = dao.transactions
    private val view by lazy { window.decorView }
    private val viewGroup by lazy { view as ViewGroup }

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
        CreateTransactionDialog(viewGroup, this)
            .createDialog(type) { createdTransaction ->
                addTransaction(createdTransaction)
                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun addTransaction(transaction: Transaction) {
        dao.addTransaction(transaction)
        updateTransactions()
    }

    private fun confirmAlterationOfTransaction(transaction: Transaction, position: Int) {
        dao.confirmAlterationOfTransaction(transaction, position)
        updateTransactions()
    }

    private fun updateTransactions() {
        createListTransaction()
        createResumeCardView()
    }

    private fun removeTransaction(position: Int) {
        dao.removeTransaction(position)
        updateTransactions()
    }

    private fun createResumeCardView() {
        val resumeView = ResumeView(this, view, transactions)
        resumeView.updateResume()
    }

    private fun createListTransaction() {
        with(transactions_listview) {
            adapter = TransactionsListAdapter(transactions, this@TransactionListActivity)

            setOnItemClickListener { _, _, position, _ ->
                val transaction = transactions[position]
                renderAlterationDialog(transaction, position)
            }

            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        if (menuId == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position = adapterMenuInfo.position
            removeTransaction(position)
        }
        return super.onContextItemSelected(item)
    }

    private fun renderAlterationDialog(transaction: Transaction, position: Int) {
        ChangeTransactionDialog(viewGroup, this)
            .createDialog(transaction) { changedTransaction ->
                confirmAlterationOfTransaction(changedTransaction, position)
            }
    }
}