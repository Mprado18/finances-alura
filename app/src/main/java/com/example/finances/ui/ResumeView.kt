package com.example.finances.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.finances.R
import com.example.finances.extension.formatCurrency
import com.example.finances.model.Resume
import com.example.finances.model.Transaction
import kotlinx.android.synthetic.main.card_resume.view.*
import java.math.BigDecimal

class ResumeView(context: Context,
                 private val view: View,
                 transactionsList: List<Transaction>) {

    private val resume: Resume = Resume(transactionsList)

    private val revenueColor = ContextCompat.getColor(context, R.color.revenue_color)
    private val expenseColor = ContextCompat.getColor(context, R.color.expense_color)

    private fun addNewRevenue() {
        val revenueTotal = resume.revenue()
        with(view.revenue_card_resume) {
            setTextColor(revenueColor)
            text = revenueTotal.formatCurrency()
        }
    }

    private fun addNewExpense() {
        val expenseTotal = resume.expense()
        with(view.expense_card_resume) {
            setTextColor(expenseColor)
            text = expenseTotal.formatCurrency()
        }
    }

    private fun addTotal() {
        val total = resume.total()
        with(view.total_card_resume) {
            if (total >= BigDecimal.ZERO) {
                setTextColor(revenueColor)
            } else {
                setTextColor(expenseColor)
            }
            text = total.formatCurrency()
        }
    }

    fun updateResume() {
        addNewRevenue()
        addNewExpense()
        addTotal()
    }
}