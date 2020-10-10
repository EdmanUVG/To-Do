package com.example.walletsaver.database

import android.content.Context
import androidx.room.*

@Database(entities = [Task::class], version = 5, exportSchema = false)
abstract class WalletDatabase: RoomDatabase() {

    abstract val taskDatabaseDao: TaskDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getInstance(context: Context): WalletDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder (
                        context.applicationContext,
                        WalletDatabase::class.java,
                        "wallet_database"
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