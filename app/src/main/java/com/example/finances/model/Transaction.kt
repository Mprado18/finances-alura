package com.example.finances.model

import java.math.BigDecimal
import java.util.Calendar

class Transaction(var value: BigDecimal,
                  val category: String = "Indefinida",
                  val type: Types,
                  val date: Calendar = Calendar.getInstance())