package com.ixafrica.netscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ixafrica.netscan.ui.theme.NetScanScreen
import com.ixafrica.netscan.ui.theme.NetScanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetScanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NetScanScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
