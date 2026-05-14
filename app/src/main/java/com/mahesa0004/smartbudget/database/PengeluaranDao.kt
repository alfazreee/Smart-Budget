package com.mahesa0004.smartbudget.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahesa0004.smartbudget.model.Pengeluaran
import kotlinx.coroutines.flow.Flow

@Dao
interface PengeluaranDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pengeluaran: Pengeluaran)

    @Query("SELECT * FROM pengeluaran ORDER BY id DESC")
    fun getAllPengeluaran(): Flow<List<Pengeluaran>>
}