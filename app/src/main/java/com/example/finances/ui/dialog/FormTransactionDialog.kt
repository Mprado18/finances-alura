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
import com.example.finances.extension.convertDateStringFormatToCalendar
import com.example.finances.extension.formatDate
import com.example.finances.model.Transaction
import com.example.finances.model.Types
import kotlinx.android.synthetic.main.transaction_form.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormTransactionDialog(
    private val context: Context,
    private val viewGroup: ViewGroup) {

    private val viewCreated = createView()
    protected val transactionValue = viewCreated.transaction_value_input
    protected val transactionDate = viewCreated.transaction_date_input
    protected val transactionCategory = viewCreated.transaction_category_spinner

    protected abstract val include: Int

    fun createDialog(type: Types, delegate: (transition: Transaction) -> Unit) {
        createDateFormated()
        createCategory(type)
        renderDialogForm(type, delegate)
    }

    private fun renderDialogForm(type: Types, delegate: (transition: Transaction) -> Unit) {
        val title = titleByCategory(type)

        AlertDialog.Builder(context)
            .setView(viewCreated)
            .setTitle(title)
            .setPositiveButton(include) { _, _ ->
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

                delegate(transactionCreated)
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
        val category = selectCategoryList(type)

        val adapter = ArrayAdapter.createFromResource(
            context,
            category,
            android.R.layout.simple_spinner_dropdown_item
        )

        transactionCategory.adapter = adapter
    }

    protected fun selectCategoryList(type: Types): Int {
        return if (type == Types.REVENUE) {
            R.array.revenue_categories
        } else {
            R.array.expense_categories
        }
    }

    protected abstract fun titleByCategory(type: Types): Int

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