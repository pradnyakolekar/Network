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
        val supportInstance1: TextView = findViewById(R.id.supportInstance)
        val bitRateRange1: TextView = findViewById(R.id.bitrange)
        val range1: TextView = findViewById(R.id.range1)
        val range21:TextView = findViewById(R.id.range2)
        val feature1:TextView = findViewById(R.id.feature2)

        val extras = intent.extras
        if (extras != null) {
            val SupportedTypes = extras.getString("name")
            val hwacc = extras.getString("hwacc")
            val swonly = extras.getString("swonly")
            val supportInstance = extras.getString("supportInstance")
            val bitRateRange = extras.getString("bitRateRange")
            val range = extras.getString("range")
            val range2=extras.getString("range212")
            val feature=extras.getString("feature")


            SupportedTypes1.text = SupportedTypes
            hwacc1.text = hwacc
            swonly1.text = swonly
            supportInstance1.text = supportInstance
            bitRateRange1.text = bitRateRange
            range1.text = range
            range21.text = range2
            feature1.text = feature

            if (SupportedTypes != null) {
                if(SupportedTypes.contains("audio")){
                    val tv1: TextView = findViewById(R.id.changetext)
                    tv1.text = "Max Input Channel"
                    val tv2:TextView = findViewById(R.id.changetext1)
                    tv2.text = "Min Input Channel"
                }
                else if(SupportedTypes.contains("video")){
                    val tv1: TextView = findViewById(R.id.changetext)
                    tv1.text = "Supported Frame Rates"
                    val tv2:TextView = findViewById(R.id.changetext1)
                    tv2.text = "Supported Performance Points"
                }

            }
        }
    }
}