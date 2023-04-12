package com.example.network

import RecyclerAdapter
import android.content.Intent
import android.media.MediaRecorder.VideoEncoder.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codeclibrary.codecUtils


class CodecInfo : AppCompatActivity(), RecyclerAdapter.CodecItemClicked, View.OnClickListener {

    private val codecdataModelArrayList: ArrayList<DataModel1> = ArrayList<DataModel1>()
    private val codecdataModelArrayList1: ArrayList<DataModel1> = ArrayList<DataModel1>()
    private val codecUtils = codecUtils()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_codec)
        val videobtn = findViewById<Button>(R.id.video)
        videobtn.setOnClickListener(this)
        val audiobtn = findViewById<Button>(R.id.audio)
        audiobtn.setOnClickListener(this)

        //codec
        for (i in codecUtils.mediaCodecList) {
            if (codecUtils.supportedTypes(i).contains("audio")) {
                codecdataModelArrayList1.add(
                    DataModel1(
                        codecUtils.name(i),
                        codecUtils.supportedTypes(i),
                        codecUtils.isHardwareAccelerated(i).toString(),
                        codecUtils.isSoftwareOnly(i).toString(),
                        "device vendor",
                        codecUtils.lowLantency(i),
                        codecUtils.maxSupportedInstances(i),
                        codecUtils.bitrateRange(i),
                        codecUtils.inputChannelCount(i),
                        codecUtils.dynamicTimestamp(i),
                        codecUtils.supportedSampleRateRanges(i),
                    )
                )
            } else {
                codecdataModelArrayList.add(
                    DataModel1(
                        codecUtils.name(i),
                        codecUtils.supportedTypes(i),
                        codecUtils.isHardwareAccelerated(i).toString(),
                        codecUtils.isSoftwareOnly(i).toString(),
                        "device vendor",
                        codecUtils.lowLantency(i),
                        codecUtils.maxSupportedInstances(i),
                        codecUtils.maxBitrate(i),
                        codecUtils.checkProfileLevels(i).toString(),
                        codecUtils.getSupportedColorFormats(i.supportedTypes[0]).toString(),
                        //codecUtils.getSupportedBitrateModes(i.name, i.supportedTypes[0]).toString(),
                        codecUtils.adaptivePlayback(i),
                    )
                )
            }
        }

        val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter(this,codecdataModelArrayList)
        recyclerview.adapter = adapter
    }

    override fun onItemClick(list: List<DataModel1>, position: Int) {
        val item = list[position]
        val intent = Intent(this, detailedcodec::class.java)
        intent.putExtra("name", item.desc)
        intent.putExtra("hwacc", item.hwAcc)
        intent.putExtra("swonly", item.swonly)
        intent.putExtra("vendor", item.vendor)
        intent.putExtra("lowlatency", item.lowlatency)
        intent.putExtra("supportInstance", item.maxInstance)
        intent.putExtra("bitRateRange", item.bitrange)
        intent.putExtra("range", item.range)
        intent.putExtra("range212", item.range2)
        intent.putExtra("feature", item.feature)
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
}



