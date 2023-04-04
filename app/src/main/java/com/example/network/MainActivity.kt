package com.example.network

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaCodecInfo
import android.media.MediaCodecList
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
            getDeviceResolution()
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
            Log.i("pradnya mime", selectCodec("673547549754925416534635").toString())
            val bundle = Bundle()
            bundle.putString("id", "codec")
            var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
            val codeclist=mediaCodecList.joinToString(separator = ",")
            Log.i("media codec", codeclist)
            printLongResponse("codec",codeclist)
            val intent = Intent(this, DeviceInfo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }


//        ntwbtn.setOnClickListener(){
//            ntwspeed.setText(getNetworkSpeed(this))
//            ntwtype.setText(getNetworkClass(this))
//        }
//        registerReceiver()

    }

    private fun printLongResponse(tagName: String, str: String?) {
        try {
            if (str != null) {
                val lengthLimit = 4000
             val loopCount = str.length / lengthLimit
             val remaining = str.length % lengthLimit
                for (i in 0 until loopCount) {
                    Log.d(tagName, "" + str.substring((lengthLimit * i), (lengthLimit * (i + 1))))
                }
            if (remaining > 0) {
                Log.d(tagName, "" + str.substring(lengthLimit * loopCount, str.length))
                }
            } else {
            Log.d(tagName, "Null Response")
        }
        } catch (e: Exception) {
            Log.e(tagName, "Error while printing long Response: $e")
        }
    }
    fun getDeviceResolution(): String {
        val displayManager = DisplayManagerCompat.getInstance(context)
        val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
        val modeCompatSupported = defaultDisplay?.let {
            DisplayCompat.getSupportedModes(context, it)
        }
        var data=""
        var size=0
        var is4kSupported = false
        if (modeCompatSupported != null) {
            for (modeCompat in modeCompatSupported) {
                Log.i("DisplaySize" ,"available"+ modeCompat.physicalWidth.toString() +" X " + modeCompat.physicalHeight.toString())
                if(size < modeCompat.physicalWidth) {
                    size=modeCompat.physicalWidth
                    data= modeCompat.physicalWidth.toString() +" X " + modeCompat.physicalHeight.toString()
                    Log.i("DisplaySize" ,data)
                }
            }
        }
        return data
    }
    private fun selectCodec(mimeType: String): MediaCodecInfo? {
        val numCodecs = MediaCodecList.getCodecCount()
        for (i in 0 until numCodecs) {
            val codecInfo = MediaCodecList.getCodecInfoAt(i)
            if (!codecInfo.isEncoder) {
                continue
            }
            val types = codecInfo.supportedTypes
            for (j in types.indices) {
                if (types[j].equals(mimeType, ignoreCase = true)) {
                    return codecInfo
                }
            }
        }
        return null
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
