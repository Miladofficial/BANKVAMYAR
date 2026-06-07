package com.miladofficial.bankvam.data.database

import androidx.room.*
import com.miladofficial.bankvam.data.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans ORDER BY id DESC")
    fun getAllLoans(): Flow<List<LoanEntity>>

    @Insert
    suspend fun insertLoan(loan: LoanEntity)

    @Update
    suspend fun updateLoan(loan: LoanEntity)

    @Delete
    suspend fun deleteLoan(loan: LoanEntity)

    @Query("SELECT SUM(amount) FROM loans")
    fun getTotalAmount(): Flow<Long?>
}