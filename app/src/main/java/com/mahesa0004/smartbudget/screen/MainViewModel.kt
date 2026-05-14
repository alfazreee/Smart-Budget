package com.mahesa0004.smartbudget.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesa0004.smartbudget.database.BudgetDao
import com.mahesa0004.smartbudget.model.Budget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: BudgetDao) : ViewModel() {

    val budget: StateFlow<Double> = dao.getBudget()
        .map { it?.amount ?: 0.0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    val spent: StateFlow<Double> = kotlinx.coroutines.flow.MutableStateFlow(0.0)
    
    fun updateBudget(newBudget: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.upsert(Budget(id = 1L, amount = newBudget))
        }
    }
}