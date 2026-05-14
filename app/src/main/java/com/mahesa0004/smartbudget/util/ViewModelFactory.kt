package com.mahesa0004.smartbudget.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahesa0004.smartbudget.database.SmartBudgetDb
import com.mahesa0004.smartbudget.screen.MainViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = SmartBudgetDb.getInstance(context).budgetDao
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}