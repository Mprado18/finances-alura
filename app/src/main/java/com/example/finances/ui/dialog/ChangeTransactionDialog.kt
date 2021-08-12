package com.example.finances.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.finances.R
import com.example.finances.extension.formatDate
import com.example.finances.model.Transaction
import com.example.finances.model.Types

class ChangeTransactionDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : FormTransactionDialog(context, viewGroup) {

    override val include: Int
        get() = R.string.change

    fun createDialog(transaction: Transaction, delegate: (transition: Transaction) -> Unit) {
        val type = transaction.type

        super.createDialog(type, delegate)

        applyValues(transaction, type)
    }

    private fun applyValues(transaction: Transaction, type: Types) {
        transactionValue.setText(transaction.value.toString())
        transactionDate.setText(transaction.date.formatDate())
        val categoriesList = context.resources.getStringArray(selectCategoryList(type))
        val categoriesPosition = categoriesList.indexOf(transaction.category)
        transactionCategory.setSelection(categoriesPosition, true)
    }

    override fun titleByCategory(type: Types): Int {
        if (type == Types.REVENUE) {
            return R.string.change_revenue
        }
        return R.string.change_expense
    }
}