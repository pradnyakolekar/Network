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

    private val codecdataModelArrayList: ArrayList<DataModel2> = ArrayList<DataModel2>()
    private val codecdataModelArrayList1: ArrayList<DataModel2> = ArrayList<DataModel2>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_codec)
        val videobtn = findViewById<Button>(R.id.video)
        videobtn.setOnClickListener(this)
        val audiobtn = findViewById<Button>(R.id.audio)
        audiobtn.setOnClickListener(this)

         val codecUtils = codecUtils(applicationContext)
        //codec
        for (i in codecUtils.mediaCodecList) {
            if (codecUtils.supportedTypes(i).contains("audio")) {
                codecdataModelArrayList.add(
                    DataModel2(
                        codecUtils.name(i),
                        codecUtils.supportedTypes(i),
                        codecUtils.isHardwareAccelerated(i).toString(),
                        codecUtils.isSoftwareOnly(i).toString(),
                        "device vendor",
                        codecUtils.lowLantency(i),
                        codecUtils.maxSupportedInstances(i),
                        codecUtils.bitrateRange(i),
                        codecUtils.inputChannelCount(i),
                        null,
                        null,
                        codecUtils.dynamicTimestamp(i),
                        codecUtils.multipleAccessFrames(i),
                        codecUtils.partialAccessUnitperIB(i),
                        codecUtils.tunneledPlayback(i),
                        null,
                        null,
                        null,
                        codecUtils.getSupportedBitrateModes(i),
                        null,
                        null,
                        null,
                        null,
                    )
                )
            } else {
                codecdataModelArrayList1.add(
                    DataModel2(
                        codecUtils.name(i),
                        codecUtils.supportedTypes(i),
                        codecUtils.isHardwareAccelerated(i).toString(),
                        codecUtils.isSoftwareOnly(i).toString(),
                        "device vendor",
                        codecUtils.lowLantency(i),
                        codecUtils.maxSupportedInstances(i),
                        codecUtils.maxBitrate(i),
                        null,
                        codecUtils.checkProfileLevels(i),
                        codecUtils.getSupportedColorFormats(i.supportedTypes[0]),
                        codecUtils.dynamicTimestamp(i),
                        codecUtils.multipleAccessFrames(i),
                        codecUtils.partialAccessUnitperIB(i),
                        codecUtils.tunneledPlayback(i),
                        codecUtils.maxResolution(i),
                        codecUtils.frameRatePerResolution(i),
                        codecUtils.maxFrameRatePerResolution(i),
                        codecUtils.getSupportedBitrateModes(i),
                        codecUtils.isHDReditsupports(i),
                        codecUtils.adaptivePlayback(i),
                        codecUtils.securePlayback(i),
                        codecUtils.infraRefresh(i)
                    )
                )
            }
        }

        val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter(this,codecdataModelArrayList1)
        recyclerview.adapter = adapter
    }

    override fun onItemClick(list: List<DataModel2>, position: Int) {
        val item = list[position]
        val intent = Intent(this, detailedcodec::class.java)
        intent.putExtra("name", item.desc)
        intent.putExtra("hwacc", item.hwAcc)
        intent.putExtra("swonly", item.swonly)
        intent.putExtra("vendor", item.vendor)
        intent.putExtra("lowlatency", item.lowlatency)
        intent.putExtra("supportInstance", item.maxInstance)
        intent.putExtra("bitRateRange", item.bitrange)
        intent.putExtra("channelCount", item.channelcount)
        intent.putExtra("profile", item.profile)
        intent.putExtra("color", item.color)
        intent.putExtra("dynamic", item.dynamic)
        intent.putExtra("multiaccess", item.multiaccess)
        intent.putExtra("tunneledframe", item.tunneledframe)
        intent.putExtra("maxReso", item.maxReso)
        intent.putExtra("maxframe", item.maxframe)
        intent.putExtra("frame", item.frame)
        intent.putExtra("bitratemodes", item.bitratemodes)
        intent.putExtra("isHDR", item.isHDR)
        intent.putExtra("adaptivePlayback", item.adaptivePlayback)
        intent.putExtra("securePlayback", item.securePlayback)
        intent.putExtra("infraRefresh", item.infraRefresh)
        intent.putExtra("partialFrame", item.partailframe)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.video -> {
                val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
                recyclerview.layoutManager = LinearLayoutManager(this)
                val adapter = RecyclerAdapter(this,codecdataModelArrayList1)
                recyclerview.adapter = adapter
            }
            R.id.audio -> {
                val recyclerview = findViewById<RecyclerView>(R.id.rvcodec)
                recyclerview.layoutManager = LinearLayoutManager(this)
                val adapter = RecyclerAdapter(this,codecdataModelArrayList)
                recyclerview.adapter = adapter
            }
        }
    }
}



