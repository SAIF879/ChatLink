package com.example.chatlink.bluetoothChat.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain{
    return BluetoothDeviceDomain(
        name = name , address = address
    )
}