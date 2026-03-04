package com.ixafrica.netscan.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ixafrica.netscan.data.NetScanApi
import com.ixafrica.netscan.data.IpInfoResponse
import kotlinx.coroutines.launch

sealed class ScanState {
    object Idle : ScanState()
    object Loading : ScanState()
    data class Success(val data: IpInfoResponse) : ScanState()
    data class Error(val message: String) : ScanState()
}

class NetScanViewModel : ViewModel() {
    private val api = NetScanApi.create()
    var uiState = mutableStateOf<ScanState>(ScanState.Idle)
        private set

    fun runScan(target: String) {
        if (target.isBlank()) return

        viewModelScope.launch {
            uiState.value = ScanState.Loading
            try {
                val result = api.getIpInfo(target)
                if (result.success) {
                    uiState.value = ScanState.Success(result)
                } else {
                    uiState.value = ScanState.Error("Target Unreachable")
                }
            } catch (e: Exception) {
                uiState.value = ScanState.Error("Network Failure: ${e.message}")
            }
        }
    }
}