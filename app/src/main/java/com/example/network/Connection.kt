package com.example.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.telephony.TelephonyManager
import android.widget.TextView
import android.widget.Toast

class Connection: BroadcastReceiver() {

    override fun onReceive(context: Context, p1: Intent?) {

         if (checkConnection(context)){
            Toast.makeText(context,"Network Connected", Toast.LENGTH_SHORT).show()
         }else{
             Toast.makeText(context,"Network Not Connected", Toast.LENGTH_SHORT).show()
         }

    }

    private fun checkConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNtw = cm.activeNetworkInfo
        return (activeNtw !=null && activeNtw.isConnectedOrConnecting)

    }

    companion object {

            //networkspeed
            fun getNetworkSpeed(context: Context): String {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val nc = cm.getNetworkCapabilities(cm.activeNetwork)
                    val downSpeed = (nc?.linkDownstreamBandwidthKbps)?.div(1000)
                    "${ downSpeed ?: 0} Mbps "
                } else {
                    "-"
                }
            }

            // networktype (2G, 4G, wifi)
            fun getNetworkClass(context: Context): String {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = cm.activeNetworkInfo
                if (info == null || !info.isConnected) return "-"
                if (info.type == ConnectivityManager.TYPE_WIFI) return "WIFI"
                if (info.type == ConnectivityManager.TYPE_MOBILE) {
                    return when (info.subtype) {
                        TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_GSM -> "2G"
                        TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_TD_SCDMA -> "3G"
                        TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN, 19 -> "4G"
                        else -> "?"
                    }
                }
                return "?"
            }
        }
}