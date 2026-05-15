package com.mahesa0004.smartbudget.screen

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahesa0004.smartbudget.R
import com.mahesa0004.smartbudget.ui.theme.SmartBudgetTheme
import com.mahesa0004.smartbudget.util.ViewModelFactory
import kotlinx.coroutines.launch

private fun shareData(context: android.content.Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbahPengeluaranScreen(
    navController: NavHostController,
    id: Long
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    var inputPengeluaran by remember { mutableStateOf("") }
    var selectedKategori by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val message = stringResource(
        R.string.bagikan_template,
        selectedKategori,
        inputPengeluaran,
        tanggal
    )
    LaunchedEffect(Unit) {
        val data = viewModel.getPengeluaranById(id)
        if (data != null) {
            inputPengeluaran = data.nominal.toLong().toString()
            selectedKategori = data.kategori
            tanggal = data.tanggal
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.ubah_pengeluaran))
                },
                actions = {
                    IconButton(
                        onClick = {
                            val nominal = inputPengeluaran.toDoubleOrNull()
                                ?: return@IconButton
                            scope.launch {
                                viewModel.updatePengeluaran(
                                    id = id,
                                    kategori = selectedKategori,
                                    nominal = nominal,
                                    tanggal = tanggal
                                )
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan)
                        )
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.hapus)) },
                            onClick = {
                                showMenu = false
                                showDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.bagikan)) },
                            onClick = {
                                showMenu = false
                                shareData(context, message)
                            }
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
            onInputChange = { inputPengeluaran = it },
            selectedKategori = selectedKategori,
            onKategoriChange = { selectedKategori = it },
            modifier = Modifier.padding(innerPadding)
        )
        if (showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    scope.launch {
                        val data = viewModel.getPengeluaranById(id)
                        if (data != null) {
                            viewModel.hapusPengeluaran(data)
                        }
                        showDialog = false
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun UbahPengeluaranPreview() {
    SmartBudgetTheme {
        UbahPengeluaranScreen(
            navController = rememberNavController(),
            id = 1L
        )
    }
}