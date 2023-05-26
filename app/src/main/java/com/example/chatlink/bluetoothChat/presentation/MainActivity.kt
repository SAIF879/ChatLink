package com.example.chatlink.bluetoothChat.presentation

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatlink.bluetoothChat.presentation.components.DeviceScreen
import com.example.chatlink.ui.theme.ChatLinkTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.jar.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bluetoothManager by lazy { applicationContext.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager?.adapter } //null check : if (device supports bluetooth then ok else null)
    private val isBlueToothEnabled : Boolean  = bluetoothAdapter?.isEnabled==true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatLinkTheme {
                val viewModal  = hiltViewModel<BluetoothViewModal>()
                val state by viewModal.state.collectAsState()

                val enableBlueToothLauncher = registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ){
                    /* Not Needed*/
                }

                val permissionLauncher = registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ){perms->
                    val canEnableBlueTooth =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                            perms[android.Manifest.permission.BLUETOOTH_CONNECT] == true
                        } else true

                    if (canEnableBlueTooth && !isBlueToothEnabled){
                        enableBlueToothLauncher.launch(
                            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        )
                    }
                }
                LaunchedEffect(key1 = true){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                        permissionLauncher.launch(
                            arrayOf(android.Manifest.permission.BLUETOOTH_SCAN , android.Manifest.permission.BLUETOOTH_CONNECT)
                        )
                    }

                }
                Surface(color = MaterialTheme.colors.background) {
                DeviceScreen(state =state , onStartScan = { viewModal::startScan } , onStopScan = {viewModal::stopScan})
                }
                }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}