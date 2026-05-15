package com.mahesa0004.smartbudget.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengeluaran")
data class Pengeluaran(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val kategori: String,
    val nominal: Double,
    val tanggal: String
)