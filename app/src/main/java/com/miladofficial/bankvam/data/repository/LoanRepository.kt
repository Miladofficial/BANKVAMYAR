package com.miladofficial.bankvam.data.repository

import com.miladofficial.bankvam.data.database.LoanDao
import com.miladofficial.bankvam.data.entity.LoanEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRepository @Inject constructor(
    private val loanDao: LoanDao
) {

    fun getAllLoans(): Flow<List<LoanEntity>> = loanDao.getAllLoans()

    suspend fun insertLoan(loan: LoanEntity) = loanDao.insertLoan(loan)

    suspend fun updateLoan(loan: LoanEntity) = loanDao.updateLoan(loan)

    suspend fun deleteLoan(loan: LoanEntity) = loanDao.deleteLoan(loan)

    fun getTotalAmount(): Flow<Long?> = loanDao.getTotalAmount()
}