package com.example.network

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class detailedcodec: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailedcodec)

        val hwacc1: TextView = findViewById(R.id.hwacc)
        val swonly1: TextView = findViewById(R.id.swonly)
        val SupportedTypes1: TextView = findViewById(R.id.supportedtypes)

        val extras = intent.extras
        if (extras != null) {
            val SupportedTypes = extras.getString("name")
            val hwacc = extras.getString("hwacc")
            val swonly = extras.getString("swonly")
            val vendor = extras.getString("vendor")

            SupportedTypes1.text = SupportedTypes
            hwacc1.text = hwacc
            swonly1.text = swonly
            //vendor1.text = vendor
        }
    }
}