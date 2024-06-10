package com.example.appmusickotlin.broadcastReceivers

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast


class BluetoothReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "BluetoothReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {


        if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
            when (state) {
                BluetoothAdapter.STATE_OFF -> Log.d(TAG, "Bluetooth OFF")
                BluetoothAdapter.STATE_TURNING_OFF -> Log.d(TAG, "Bluetooth TURNING OFF")
                BluetoothAdapter.STATE_ON -> Log.d(TAG, "Bluetooth ON")
                BluetoothAdapter.STATE_TURNING_ON -> Log.d(TAG, "Bluetooth TURNING ON")
            }

        }

        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            // Kiểm tra trạng thái chế độ máy bay
            val isAirplaneModeOn = intent.getBooleanExtra(Settings.Global.AIRPLANE_MODE_ON, false)
            if (isAirplaneModeOn) {
                // Chế độ máy bay đang bật
                // Hiển thị thông báo hoặc thực hiện hành động khác
                Toast.makeText(context, "Chế độ máy bay đang bật", Toast.LENGTH_SHORT).show()
            } else {
                // Chế độ máy bay đang tắt
                // Hiển thị thông báo hoặc thực hiện hành động khác
                Toast.makeText(context, "Chế độ máy bay đang tắt", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
