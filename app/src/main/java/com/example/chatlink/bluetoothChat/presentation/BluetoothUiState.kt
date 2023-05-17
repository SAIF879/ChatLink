package com.example.chatlink.bluetoothChat.presentation

import com.example.chatlink.bluetoothChat.domain.chat.BluetoothDevice

data class BluetoothUiState(
    val scannedDevices  : List<BluetoothDevice> = emptyList(),
    val connectedDevices : List<BluetoothDevice> = emptyList()
)