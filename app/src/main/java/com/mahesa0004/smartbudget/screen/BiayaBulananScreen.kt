package com.mahesa0004.smartbudget.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahesa0004.smartbudget.R
import com.mahesa0004.smartbudget.ui.theme.SmartBudgetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiayaBulananScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val budget by viewModel.budget.collectAsState()
    var budgetInput by remember { mutableStateOf(budget.toLong().toString()) }

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
                    Text(text = stringResource(R.string.biaya_bulanan))
                },
                actions = {
                    IconButton(onClick = {
                        val newBudget = budgetInput.toDoubleOrNull()
                        if (newBudget != null) {
                            viewModel.updateBudget(newBudget)
                        }
                        navController.popBackStack()
                    }) {
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
        FormBiayaBulanan(
            budgetInput = budgetInput,
            onBudgetChange = { budgetInput = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FormBiayaBulanan(
    budgetInput: String,
    onBudgetChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = budgetInput,
            onValueChange = { onBudgetChange(it) },
            label = { Text(text = stringResource(R.string.budget)) },
            suffix = { Text(text = stringResource(R.string.per_bulan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BiayaBulananScreenPreview() {
    SmartBudgetTheme {
        BiayaBulananScreen(rememberNavController())
    }
}