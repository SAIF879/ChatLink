package com.example.chatlink.bluetoothChat.data.chat

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.content.getSystemService
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothController
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothDeviceDomain
import kotlinx.coroutines.flow.*
@SuppressLint("MissingPermission")
class AndroidBluetoothController (private val context : Context) : BluetoothController {

    private val bluetoothManager by lazy { context.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager?.adapter } //null check : if (device supports bluetooth then ok else null)


    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get()= _scannedDevices.asStateFlow()

    private val _connectedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val connectedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _connectedDevices.asStateFlow()


     private val foundDeviceReceiver = FoundDeviceReceiver{device->
         _scannedDevices.update {devices->
             val newdevice = device.toBluetoothDeviceDomain()
             if (newdevice in devices) devices else devices+ newdevice
         }
     }
    init {
        updatePairedDevices()
    }



    override fun startDiscovery() {
       if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) return
        else
            context.registerReceiver(foundDeviceReceiver , IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND))
            updatePairedDevices()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
    if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){return}
       bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
      context.unregisterReceiver(foundDeviceReceiver)
    }


    private fun updatePairedDevices(){
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)){
            return
        }
        bluetoothAdapter?.bondedDevices?.map {
            it.toBluetoothDeviceDomain() }
            ?.also {devices->
                _connectedDevices.update { devices } }
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}