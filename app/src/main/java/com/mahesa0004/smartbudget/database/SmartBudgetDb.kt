package com.mahesa0004.smartbudget.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahesa0004.smartbudget.model.Budget
import com.mahesa0004.smartbudget.model.Pengeluaran

@Database(
    entities = [
        Budget::class,
        Pengeluaran::class
    ],
    version = 2,
    exportSchema = false
)
abstract class SmartBudgetDb : RoomDatabase() {

    abstract val budgetDao: BudgetDao
    abstract val pengeluaranDao: PengeluaranDao
    companion object {
        @Volatile
        private var INSTANCE: SmartBudgetDb? = null

        fun getInstance(context: Context): SmartBudgetDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SmartBudgetDb::class.java,
                        "smartbudget.db"
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