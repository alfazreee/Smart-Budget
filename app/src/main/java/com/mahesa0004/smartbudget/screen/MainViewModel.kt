package com.mahesa0004.smartbudget.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesa0004.smartbudget.database.BudgetDao
import com.mahesa0004.smartbudget.database.PengeluaranDao
import com.mahesa0004.smartbudget.model.Budget
import com.mahesa0004.smartbudget.model.Pengeluaran
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(
    private val budgetDao: BudgetDao,
    private val pengeluaranDao: PengeluaranDao
) : ViewModel() {

    val budget: StateFlow<Double> =
        budgetDao.getBudget()
            .map { it?.amount ?: 0.0 }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )

    val pengeluaranList: StateFlow<List<Pengeluaran>> =
        pengeluaranDao.getAllPengeluaran()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val spent: StateFlow<Double> =
        pengeluaranDao.getAllPengeluaran()
            .map { list ->
                list.sumOf { it.nominal }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )

    fun updateBudget(newBudget: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetDao.upsert(
                Budget(
                    id = 1L,
                    amount = newBudget
                )
            )
        }
    }

    fun tambahPengeluaran(
        kategori: String,
        nominal: Double
    ) {
        val tanggal = SimpleDateFormat(
            "dd-MM-yyyy   HH:mm:ss",
            Locale.getDefault()
        ).format(Date())
        viewModelScope.launch(Dispatchers.IO) {
            pengeluaranDao.insert(
                Pengeluaran(
                    kategori = kategori,
                    nominal = nominal,
                    tanggal = tanggal
                )
            )
        }
    }

    suspend fun getPengeluaranById(
        id: Long
    ): Pengeluaran? {

        return pengeluaranDao
            .getPengeluaranById(id)
    }

    fun updatePengeluaran(
        id: Long,
        kategori: String,
        nominal: Double,
        tanggal: String
    ) {
        viewModelScope.launch(Dispatchers.IO) { pengeluaranDao.update(
            Pengeluaran(
                    id = id,
                    kategori = kategori,
                    nominal = nominal,
                    tanggal = tanggal
                )
            )
        }
    }

    fun hapusPengeluaran(
        pengeluaran: Pengeluaran
    ) { viewModelScope.launch(Dispatchers.IO) { pengeluaranDao.delete(pengeluaran) }
    }
}