package com.example.network

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaDrm
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.hardware.display.DisplayManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.Button
import android.widget.TextView
import com.example.network.Connection.Companion.getNetworkClass
import com.example.network.Connection.Companion.getNetworkSpeed
import java.util.*


class MainActivity : AppCompatActivity(){

    val receiver: Connection = Connection()
    var context = this
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ntwspeed = findViewById<TextView>(R.id.ntwspeed)
        val ntwbtn = findViewById<Button>(R.id.checknetworkbtn)
        val ntwtype = findViewById<TextView>(R.id.ntwtype)

        screenValue()

        var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
        val codeclist=mediaCodecList.joinToString(separator = ",")
        Log.i("media codec", codeclist)


        val WIDEVINE_UUID = UUID(-0x121074568629b532L,-0x5c37d8232ae2de13L)
        val mediaDrm = MediaDrm(WIDEVINE_UUID)
        val vendor = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VENDOR)
        val version = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VERSION)
        val description = mediaDrm.getPropertyString(MediaDrm.PROPERTY_DESCRIPTION)
        val algorithms = mediaDrm.getPropertyString(MediaDrm.PROPERTY_ALGORITHMS)
        //val securityLevel=mediaDrm.getPropertyString("SecurityLevel")
        var hdcp: String? = null
        val securityLevel = mediaDrm.getPropertyString("securityLevel")
        val systemId = mediaDrm.getPropertyString("systemId")
        val hdcpLevel = mediaDrm.getPropertyString("hdcpLevel")
        val maxHdcpLevel = mediaDrm.getPropertyString("maxHdcpLevel")
        val usageReportingSupport = mediaDrm.getPropertyString("usageReportingSupport")
        val maxNumberOfSessions = mediaDrm.getPropertyString("maxNumberOfSessions")
        val numberOfOpenSessions = mediaDrm.getPropertyString("numberOfOpenSessions")
        val display=windowManager.defaultDisplay
        val displayId = windowManager.defaultDisplay.displayId
        val refreshRate=windowManager.defaultDisplay.refreshRate
//        val deviceInfo=windowManager.defaultDisplay.deviceProductInfo
//        val name=windowManager.defaultDisplay.name.toString()
//        val hdr=windowManager.defaultDisplay.isHdr
//        val hdrCapabilities=windowManager.defaultDisplay.hdrCapabilities
//        val crypto=mediaDrm.metrics.toString()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         hdcp = mediaDrm.connectedHdcpLevel.toString()
         }

        Log.i("WideVine",
            "Vendor-$vendor Version-$version desc-$description Algorithn-$algorithms HDCP-$hdcp Security Level-$securityLevel SystemId-$systemId HDCPLevel-$hdcpLevel MaxHDCPLevel-$maxHdcpLevel usageReportingSupport-$usageReportingSupport maxNumberOfSessions-$maxNumberOfSessions numberOfOpenSessions-$numberOfOpenSessions")
        Log.i("Display",
        "displayId-$displayId \n refreshRate-$refreshRate ")
//      deviceInfo-$deviceInfo name-$name hdr-$hdr hdrCapabilities-$hdrCapabilities")
        Log.i("Display","$display")


        ntwbtn.setOnClickListener(){
            ntwspeed.setText(getNetworkSpeed(this))
            ntwtype.setText(getNetworkClass(this))
        }
        registerReceiver()



    }
    private fun screenValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val defaultDisplay =
                DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
            val displayContext = createDisplayContext(defaultDisplay!!)

            val width = displayContext.resources.displayMetrics.widthPixels
            val height = displayContext.resources.displayMetrics.heightPixels

            Log.e("tag", "width (ANDOIRD R/ABOVE): $width")
            Log.e("tag", "height (ANDOIRD R/ABOVE) : $height")

        } else {

            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels

            Log.e("tag", "width (BOTTOM ANDROID R): $width")
            Log.e("tag", "height (BOTTOM ANDROID R) : $height")

        }
    }


    private fun registerReceiver() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    @SuppressLint("HardwareIds")
    private fun getSystemDetail(): String {
        return "Brand: ${Build.BRAND} \n" +
                "DeviceID: ${
                    Settings.Secure.getString(
                        contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                } \n" +
                "Model: ${Build.MODEL} \n" +
                "ID: ${Build.ID} \n" +
                "SDK: ${Build.VERSION.SDK_INT} \n" +
                "Manufacture: ${Build.MANUFACTURER} \n" +
                "Brand: ${Build.BRAND} \n" +
                "User: ${Build.USER} \n" +
                "Type: ${Build.TYPE} \n" +
                "Base: ${Build.VERSION_CODES.BASE} \n" +
                "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
                "Board: ${Build.BOARD} \n" +
                "Host: ${Build.HOST} \n" +
                "FingerPrint: ${Build.FINGERPRINT} \n" +
                "Version Code: ${Build.VERSION.RELEASE} "
    }

    private fun unregisterReceiver(){
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver()
    }
}
