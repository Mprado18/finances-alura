package com.example.finances.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.finances.R
import com.example.finances.extension.formatCategory
import com.example.finances.extension.formatCurrency
import com.example.finances.extension.formatDate
import com.example.finances.model.Transaction
import com.example.finances.model.Types
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionsListAdapter(private val transactions: List<Transaction>,
                              private val context: Context) : BaseAdapter() {

    private val stringCategoryLimit = 15

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCreated = LayoutInflater.from(context)
                .inflate(R.layout.transaction_item, parent, false)

        val transaction = transactions[position]

        if (transaction.type == Types.REVENUE) {
            viewCreated.transaction_value.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.revenue_color))
            viewCreated.transaction_icon.setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            viewCreated.transaction_value.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.expense_color))
            viewCreated.transaction_icon.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        }

        viewCreated.transaction_value.text = transaction.value.formatCurrency()
        viewCreated.transaction_category.text = transaction.category.formatCategory(stringCategoryLimit)
        viewCreated.transaction_data.text = transaction.date.formatDate()

        return viewCreated
    }

    override fun getItem(position: Int): Transaction {
        return transactions[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transactions.size
    }
}