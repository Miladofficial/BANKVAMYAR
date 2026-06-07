package com.miladofficial.bankvam.di

import android.content.Context
import androidx.room.Room
import com.miladofficial.bankvam.data.database.LoanDao
import com.miladofficial.bankvam.data.database.LoanDatabase
import com.miladofficial.bankvam.data.repository.LoanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoanDatabase(@ApplicationContext context: Context): LoanDatabase {
        return Room.databaseBuilder(
            context,
            LoanDatabase::class.java,
            "loan_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoanDao(database: LoanDatabase): LoanDao {
        return database.loanDao()
    }

    @Provides
    @Singleton
    fun provideLoanRepository(repository: LoanRepository): LoanRepository {
        return repository
    }
}