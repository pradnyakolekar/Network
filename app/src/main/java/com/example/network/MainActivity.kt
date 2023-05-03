package com.example.network

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val btndisplay = findViewById<Button>(R.id.display)
        val btndrm = findViewById<Button>(R.id.drm)
        val btnnetwork=findViewById<Button>(R.id.network)
        val btncodec = findViewById<Button>(R.id.codec)
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

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

        btnnetwork.setOnClickListener(){
            val bundle = Bundle()
            bundle.putString("id", "network")
            val intent = Intent(this, DeviceInfo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        btncodec.setOnClickListener(){
            val bundle = Bundle()
            bundle.putString("id", "codec")
            val intent = Intent(this, CodecInfo::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
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
}
