package com.example.chatlink.bluetoothChat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class BluetoothViewModal @Inject constructor(private val bluetoothController: BluetoothController) : ViewModel() {
private val _state = MutableStateFlow(BluetoothUiState())
    val state = combine(
        bluetoothController.scannedDevices,
        bluetoothController.connectedDevices,
        _state
    ){scannedDevices , connectedDevices , state->
        state.copy(
            scannedDevices = scannedDevices ,
            connectedDevices = connectedDevices
        )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000) , _state.value)

    fun startScan(){
        bluetoothController.startDiscovery()
    }

    fun stopScan(){
        bluetoothController.stopDiscovery()
    }
}