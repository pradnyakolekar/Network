package com.example.network

import RecyclerAdapter
import android.content.Intent
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CodecInfo : AppCompatActivity(), RecyclerAdapter.CodecItemClicked, View.OnClickListener {

    val codecdataModelArrayList: ArrayList<DataModel1> = ArrayList<DataModel1>()
    val codecdataModelArrayList1: ArrayList<DataModel1> = ArrayList<DataModel1>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_codec)
        val videobtn = findViewById<Button>(R.id.video)
        videobtn.setOnClickListener(this)
        val audiobtn = findViewById<Button>(R.id.audio)
        audiobtn.setOnClickListener(this)


        //codec
        var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
        val codeclist=mediaCodecList.joinToString(separator = ",")
        Log.i("media codec", codeclist)
        printLongResponse("codec",codeclist)
        for (i in mediaCodecList) {
            //codecdataModelArrayList.add(DataModel( i.name,"click to get more details"))
            val instance = MediaCodecInfo.CodecCapabilities.FEATURE_LowLatency

            if (i.supportedTypes.get(0).contains("audio")) {
                val stor = i.getCapabilitiesForType(i.supportedTypes.get(0)).audioCapabilities.bitrateRange
                Log.i("Decoder low lat ", stor.toString())

                codecdataModelArrayList1.add(
                    DataModel1(
                        i.name,
                        i.supportedTypes.get(0),
                        i.isHardwareAccelerated.toString(),
                        i.isSoftwareOnly.toString(),
                        i.isVendor.toString()
                    )
                )
            } else{
                codecdataModelArrayList.add(
                    DataModel1(
                        i.name,
                        i.supportedTypes.get(0),
                        i.isHardwareAccelerated.toString(),
                        i.isSoftwareOnly.toString(),
                        i.isVendor.toString()
                    )
                )
            }
        }

        val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter(this,codecdataModelArrayList)
        recyclerview.adapter = adapter
    }

    override fun onItemClick(list2: List<DataModel1>, position: Int) {
        val item = list2[position]
        val intent = Intent(this, detailedcodec::class.java)
        intent.putExtra("name", item.desc)
        intent.putExtra("hwacc", item.hwAcc)
        intent.putExtra("swonly", item.swonly)
        intent.putExtra("vendor", item.vendor)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.video -> {
                val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
                recyclerview.layoutManager = LinearLayoutManager(this)
                val adapter = RecyclerAdapter(this,codecdataModelArrayList)
                recyclerview.adapter = adapter
            }
            R.id.audio -> {
                val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
                recyclerview.layoutManager = LinearLayoutManager(this)
                val adapter = RecyclerAdapter(this,codecdataModelArrayList1)
                recyclerview.adapter = adapter
            }
        }
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
}
