package com.scoqu.lasw.egasy.domain.use_case.user

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.provider.Settings

class UserInteractorImpl : UserInteractor {

    override fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun isUser(context: Context): Boolean {
        val adb = Settings.Secure.getInt(
            context.applicationContext.contentResolver,
            Settings.Global.ADB_ENABLED,
            0
        ) != 0

        val manager = context.applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batLevel = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        val charging = context.applicationContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0

        return adb || (batLevel == 100 && charging)
    }
}