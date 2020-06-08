package com.example.walletsaver.database

import android.content.Context
import androidx.room.*

@Database(entities = [Budget::class], version = 8, exportSchema = false)
abstract class BudgetDatabase: RoomDatabase() {

    abstract val budgetDatabaseDao: BudgetDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder (
                        context.applicationContext,
                        BudgetDatabase::class.java,
                        "budget_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}