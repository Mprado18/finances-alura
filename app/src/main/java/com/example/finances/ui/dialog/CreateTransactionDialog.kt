package com.example.finances.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.finances.R
import com.example.finances.delegate.TransactionDelegate
import com.example.finances.extension.convertDateStringFormatToCalendar
import com.example.finances.extension.formatDate
import com.example.finances.model.Transaction
import com.example.finances.model.Types
import kotlinx.android.synthetic.main.transaction_form.view.*
import java.math.BigDecimal
import java.util.*

class CreateTransactionDialog(private val viewGroup: ViewGroup, private val context: Context) {

    private val viewCreated = createView()

    private val transactionValue = viewCreated.transaction_value_input
    private val transactionDate = viewCreated.transaction_date_input
    private val transactionCategory = viewCreated.transaction_category_spinner

    fun createDialog(type: Types, transactionDelegate: TransactionDelegate) {
        createDateFormated()
        createCategory(type)
        renderDialogForm(type, transactionDelegate)
    }

    private fun renderDialogForm(type: Types, transactionDelegate: TransactionDelegate) {
        val title = if (type == Types.REVENUE) {
            R.string.add_revenue
        } else {
            R.string.add_expense
        }

        AlertDialog.Builder(context)
            .setView(viewCreated)
            .setTitle(title)
            .setPositiveButton("Incluir") { _, _ ->
                val value = transactionValue.text.toString()
                val date = transactionDate.text.toString()
                val category = transactionCategory.selectedItem.toString()

                val valueToConvert = convertValue(value)
                val dateConverted = date.convertDateStringFormatToCalendar()

                val transactionCreated = Transaction(
                    type = type,
                    value = valueToConvert,
                    date = dateConverted,
                    category = category
                )

                transactionDelegate.delegate(transactionCreated)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun convertValue(value: String): BigDecimal {
        return try {
            BigDecimal(value)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Valor zerado", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

    private fun createCategory(type: Types) {
        val category = if (type == Types.REVENUE) {
            R.array.revenue_categories
        } else {
            R.array.expense_categories
        }

        val adapter = ArrayAdapter.createFromResource(
            context,
            category,
            android.R.layout.simple_spinner_dropdown_item
        )

        transactionCategory.adapter = adapter
    }

    private fun createDateFormated() {
        val currentDate = Calendar.getInstance()

        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        transactionDate.setText(currentDate.formatDate())

        transactionDate.setOnClickListener {
            DatePickerDialog(context, { _, year, month, dayOfMonth ->
                val dateSelected = Calendar.getInstance()
                dateSelected.set(
                    year,
                    month,
                    dayOfMonth
                )

                transactionDate.setText(dateSelected.formatDate())

            }, year, month, day).show()
        }
    }

    private fun createView(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.transaction_form,
            viewGroup,
            false
        )
    }
}