package com.example.network

import android.annotation.SuppressLint
import android.content.Intent
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
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btndisplay = findViewById<Button>(R.id.display)
        val btndrm = findViewById<Button>(R.id.drm)
        val btncodec = findViewById<Button>(R.id.codec)

        btndisplay.setOnClickListener(){
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
            val bundle = Bundle()
            bundle.putString("id", "codec")
            var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
            val codeclist=mediaCodecList.joinToString(separator = ",")
            Log.i("media codec", codeclist)
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
