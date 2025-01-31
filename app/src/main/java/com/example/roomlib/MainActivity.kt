package com.example.roomlib

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.roomlib.ui.theme.RoomLibTheme
import kotlin.getValue
import kotlin.jvm.java

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            TelContactDatabase::class.java,
            name = "TelContact.db"
        ).build()
    }

    private val viewModel by viewModels<TelContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TelContactViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomLibTheme {
                val state = viewModel.state.collectAsState().value
                MainScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}

