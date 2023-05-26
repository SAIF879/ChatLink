package com.example.chatlink.bluetoothChat.presentation.components

import android.app.admin.DnsEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatlink.bluetoothChat.domain.chat.BluetoothDevice
import com.example.chatlink.bluetoothChat.presentation.BluetoothUiState

@Composable
fun  DeviceScreen(
    state : BluetoothUiState,
    onStartScan : () -> Unit,
    onStopScan : () -> Unit
) {
    
    Column(modifier = Modifier.fillMaxSize()) {
        
        Row(horizontalArrangement = Arrangement.SpaceAround , modifier = Modifier.fillMaxWidth()) {
            
            Button(onClick = {onStartScan.invoke()}) {
                Text(text = "Start Scan")
            }
            Button(onClick = {onStopScan.invoke()}) {
                Text(text = "Stop Scan")
            }
            
        }

    }

}

@Composable
fun BlueToothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier) {
        //paired devices
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = modifier.padding(16.dp)
            )
        }
   
        items(pairedDevices){device->
            
            Text(text = device.name ?: "No Name" , modifier = modifier.fillMaxWidth().padding(16.dp).clickable { onClick.invoke(device) } ,)

        }

        //scanned devices
        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = modifier.padding(16.dp)
            )
        }

        items(scannedDevices){device->

            Text(text = device.name ?: "No Name" , modifier = modifier.fillMaxWidth().padding(16.dp).clickable { onClick.invoke(device) } ,)


        }


    }
}
