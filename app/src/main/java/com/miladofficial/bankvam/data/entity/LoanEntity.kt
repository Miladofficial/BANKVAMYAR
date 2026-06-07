package com.miladofficial.bankvam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bankName: String,
    val loanType: String,
    val amount: Long,
    val installments: Int,
    val interestRate: Double,
    val startDate: String,
    val paidInstallments: Int = 0,
    val note: String = ""
)