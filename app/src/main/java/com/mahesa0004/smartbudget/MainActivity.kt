package com.mahesa0004.smartbudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.mahesa0004.smartbudget.navigation.SetupNavGraph
import com.mahesa0004.smartbudget.ui.theme.SmartBudgetTheme
import com.mahesa0004.smartbudget.util.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            val dataStore = SettingsDataStore(context)
            val isDarkTheme by dataStore.themeFlow.collectAsState(initial = false)

            SmartBudgetTheme(darkTheme = isDarkTheme) {
                SetupNavGraph(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveTheme(it)
                        }
                    }
                )
            }
        }
    }
}