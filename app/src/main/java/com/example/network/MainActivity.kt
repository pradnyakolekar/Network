package com.example.network

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaFormat
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Display
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.view.DisplayCompat
import java.util.*


class MainActivity : AppCompatActivity(){

    val receiver: Connection = Connection()
    var context = this
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btndisplay = findViewById<Button>(R.id.display)
        val btndrm = findViewById<Button>(R.id.drm)
        val btncodec = findViewById<Button>(R.id.codec)

        btndisplay.setOnClickListener(){
            //getDeviceResolution()
            val bundle = Bundle()
            bundle.putString("id", "display")
            val intent = Intent(this, DeviceInfo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        btndrm.setOnClickListener(){
            val bundle = Bundle()
            bundle.putString("id", "drm")
            val intent = Intent(this, DeviceInfo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        btncodec.setOnClickListener(){
            Log.i("pradnya codec info" ,getSupportedVideoCodecs().toString())
            val bundle = Bundle()
            bundle.putString("id", "codec")
            val intent = Intent(this, CodecInfo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }


//        ntwbtn.setOnClickListener(){
//            ntwspeed.setText(getNetworkSpeed(this))
//            ntwtype.setText(getNetworkClass(this))
//        }
//        registerReceiver()

    }

    fun getSupportedVideoCodecs(): List<String> {
        val codecList = MediaCodecList(MediaCodecList.REGULAR_CODECS)
        val codecInfos = codecList.codecInfos
        val supportedCodecs = mutableListOf<String>()

        for (codecInfo in codecInfos) {
            if (codecInfo.isEncoder) {
                continue
            }

            val types = codecInfo.supportedTypes
            for (type in types) {
                if (type.startsWith("video/")) {
                    val format = MediaFormat.createVideoFormat(type, 640, 480) // set some arbitrary resolution
                    if (codecInfo.getCapabilitiesForType(type).isFormatSupported(format)) {
                        supportedCodecs.add(codecInfo.name)
                        break
                    }
                }
            }
        }

        return supportedCodecs
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
    private fun getSystemDetail() : String{
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
