package com.ixafrica.netscan.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ixafrica.netscan.data.IpInfoResponse
import com.ixafrica.netscan.viewmodel.NetScanViewModel
import com.ixafrica.netscan.viewmodel.ScanState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetScanScreen(
    modifier: Modifier = Modifier,
    viewModel: NetScanViewModel = viewModel()
) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(20.dp)
    ) {
        // Header
        Text(
            "NETSCAN v1.0_MOBILE",
            color = Color(0xFF00FF41), // Matrix Green
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            "DATA CENTRE OPS DIAGNOSTICS",
            color = Color(0xFF00FF41).copy(0.4f),
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input Module
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("TARGET_ACQUISITION", color = Color(0xFF00FF41).copy(0.5f)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontFamily = FontFamily.Monospace, color = Color.White),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00FF41),
                unfocusedBorderColor = Color(0xFF00FF41).copy(0.3f),
                cursorColor = Color(0xFF00FF41),
                focusedLabelColor = Color(0xFF00FF41),
                unfocusedLabelColor = Color(0xFF00FF41).copy(0.5f)
            )
        )

        Button(
            onClick = { viewModel.runScan(query) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF41))
        ) {
            Text("EXECUTE_SCAN", color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Results Section
        Box(modifier = Modifier.fillMaxSize()) {
            when (val s = state) {
                is ScanState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF00FF41)
                    )
                }
                is ScanState.Error -> {
                    Text(
                        "ERROR: ${s.message}",
                        color = Color.Red,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                is ScanState.Success -> {
                    ResultCard(s.data)
                }
                is ScanState.Idle -> {
                    Text(
                        "AWAITING TARGET INPUT...",
                        color = Color(0xFF00FF41).copy(0.2f),
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun ResultCard(data: IpInfoResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A0A)),
        border = BorderStroke(1.dp, Color(0xFF00FF41).copy(0.3f)),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "INTELLIGENCE_REPORT",
                color = Color(0xFF00FF41),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            InfoText("IP_ADDR", data.ip)
            InfoText("ISP_NAME", data.isp ?: "N/A")
            InfoText("ASN_CODE", data.asn ?: "N/A")
            val location = if (data.city != null && data.country != null) "${data.city}, ${data.country}" else "UNKNOWN"
            InfoText("LOCATION", location)
            InfoText("ORG_NAME", data.org ?: "N/A")
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("$label: ", color = Color(0xFF00FF41).copy(0.5f), fontFamily = FontFamily.Monospace)
        Text(value, color = Color.White, fontFamily = FontFamily.Monospace)
    }
}
