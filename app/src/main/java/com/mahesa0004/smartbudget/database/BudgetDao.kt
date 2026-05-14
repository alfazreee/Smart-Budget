package com.mahesa0004.smartbudget.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahesa0004.smartbudget.model.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(budget: Budget)

    @Query("SELECT * FROM budget WHERE id = 1")
    fun getBudget(): Flow<Budget?>
}