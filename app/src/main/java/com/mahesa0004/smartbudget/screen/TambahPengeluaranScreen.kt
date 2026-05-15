package com.mahesa0004.smartbudget.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahesa0004.smartbudget.R
import com.mahesa0004.smartbudget.ui.theme.SmartBudgetTheme
import com.mahesa0004.smartbudget.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPengeluaranScreen(navController: NavHostController) {

    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val budget by viewModel.budget.collectAsState()
    val spent by viewModel.spent.collectAsState()
    var inputPengeluaran by remember { mutableStateOf("") }
    var selectedKategori by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.tambah_pengeluaran))
                },
                actions = {
                    IconButton(
                        onClick = {
                            val nominal = inputPengeluaran.toDoubleOrNull()
                            if (nominal == null || nominal <= 0 || selectedKategori.isBlank()) { Toast.makeText(context,
                                    context.getString(R.string.input_pengeluaran),
                                    Toast.LENGTH_SHORT).show()
                                return@IconButton
                            }
                            val sisaBudget = budget - spent
                            if (nominal > sisaBudget) { Toast.makeText(context, "Budget tidak cukup", Toast.LENGTH_SHORT).show()
                                return@IconButton
                            }
                            viewModel.tambahPengeluaran(
                                kategori = selectedKategori,
                                nominal = nominal
                            )
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        FormPengeluaran(
            inputPengeluaran = inputPengeluaran,
            onInputChange = {
                inputPengeluaran = it
            },
            selectedKategori = selectedKategori,
            onKategoriChange = {
                selectedKategori = it
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FormPengeluaran(
    inputPengeluaran: String,
    onInputChange: (String) -> Unit,
    selectedKategori: String,
    onKategoriChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val kategoriList = listOf(
        stringResource(R.string.makanan),
        stringResource(R.string.transportasi),
        stringResource(R.string.belanja),
        stringResource(R.string.hiburan),
        stringResource(R.string.tagihan),
        stringResource(R.string.tabungan),
        stringResource(R.string.lainnya)
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = inputPengeluaran,
            onValueChange = {
                onInputChange(it)
            },
            label = {
                Text(text = stringResource(R.string.input_pengeluaran))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        kategoriList.forEach { kategori ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedKategori == kategori,
                    onClick = {
                        onKategoriChange(kategori)
                    }
                )
                Text(text = kategori)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun TambahPengeluaranScreenPreview() {
    SmartBudgetTheme {
        TambahPengeluaranScreen(
            rememberNavController()
        )
    }
}