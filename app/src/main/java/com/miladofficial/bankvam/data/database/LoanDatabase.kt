package com.miladofficial.bankvam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miladofficial.bankvam.data.entity.LoanEntity

@Database(
    entities = [LoanEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LoanDatabase : RoomDatabase() {

    abstract fun loanDao(): LoanDao

    companion object {
        @Volatile
        private var INSTANCE: LoanDatabase? = null

        fun getInstance(context: Context): LoanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LoanDatabase::class.java,
                    "loan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}