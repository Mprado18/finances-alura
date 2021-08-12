package com.example.finances.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.finances.R
import com.example.finances.model.Types

class CreateTransactionDialog(viewGroup: ViewGroup,
                              context: Context) : FormTransactionDialog(context, viewGroup) {
    override val include: Int
        get() = R.string.include

    override fun titleByCategory(type: Types): Int {
        if (type == Types.REVENUE) {
            return R.string.add_revenue
        }
        return R.string.add_expense
    }
}