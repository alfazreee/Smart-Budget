package com.mahesa0004.smartbudget.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(
    @PrimaryKey
    val id: Long = 1L,
    val amount: Double = 0.0
)