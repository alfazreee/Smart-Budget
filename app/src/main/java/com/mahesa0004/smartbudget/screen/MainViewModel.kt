package com.mahesa0004.smartbudget.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _budget = MutableStateFlow(1500000.0)
    val budget: StateFlow<Double> = _budget

    private val _spent = MutableStateFlow(600000.0)
    val spent: StateFlow<Double> = _spent

    fun updateBudget(newBudget: Double) {
        _budget.value = newBudget
    }
}