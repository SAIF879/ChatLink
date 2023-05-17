package com.example.chatlink.bluetoothChat.data.chat

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.getSystemService
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothController
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothDeviceDomain
import kotlinx.coroutines.flow.*

class AndroidBluetoothController (private val context : Context) : BluetoothController {

    private val bluetoothManager by lazy { context.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager?.adapter } //null check : if (device supports bluetooth then ok else null)

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
    get()= _scannedDevices.asStateFlow()

    private val _connectedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val connectedDevices: StateFlow<List<BluetoothDeviceDomain>>
    get() = _connectedDevices.asStateFlow()

    override fun startDiscovery() {
        TODO("Not yet implemented")
    }

    override fun stopDiscovery() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}