package com.example.network

import RecyclerAdapter
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.annotation.TargetApi
import android.content.Intent
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaRecorder.VideoEncoder.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CodecInfo : AppCompatActivity(), RecyclerAdapter.CodecItemClicked, View.OnClickListener {

    val codecdataModelArrayList: ArrayList<DataModel1> = ArrayList<DataModel1>()
    val codecdataModelArrayList1: ArrayList<DataModel1> = ArrayList<DataModel1>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_codec)
        val videobtn = findViewById<Button>(R.id.video)
        videobtn.setOnClickListener(this)
        val audiobtn = findViewById<Button>(R.id.audio)
        audiobtn.setOnClickListener(this)

        //codec
        var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
        
        for (i in mediaCodecList) {

            if (i.supportedTypes[0].contains("audio")) {
                codecdataModelArrayList1.add(
                    DataModel1(
                        i.name,
                        i.supportedTypes[0],
                        isHardwareAccelerated(i).toString(),
                        isSoftwareOnly(i).toString(),
                        "Google",
                        i.getCapabilitiesForType(i.supportedTypes[0]).maxSupportedInstances.toString(),
                        i.getCapabilitiesForType(i.supportedTypes[0]).audioCapabilities.bitrateRange.toString(),
                        i.getCapabilitiesForType(i.supportedTypes[0]).audioCapabilities.maxInputChannelCount.toString(),
                        i.getCapabilitiesForType(i.supportedTypes[0]).audioCapabilities.minInputChannelCount.toString(),
                        supportedSampleRateRanges(i),
                    )
                )
            } else {
                codecdataModelArrayList.add(
                    DataModel1(
                        i.name,
                        i.supportedTypes[0],
                        isHardwareAccelerated(i).toString(),
                        isSoftwareOnly(i).toString(),
                        "Google",
                        i.getCapabilitiesForType(i.supportedTypes[0]).maxSupportedInstances.toString(),
                        i.getCapabilitiesForType(i.supportedTypes[0]).videoCapabilities.bitrateRange.toString(),
                        i.getCapabilitiesForType(i.supportedTypes[0]).videoCapabilities.supportedFrameRates.toString(),
                        "i.getCapabilitiesForType(i.supportedTypes[0]).videoCapabilities.supportedPerformancePoints.toString()",
                        i.getCapabilitiesForType(i.supportedTypes[0]).videoCapabilities.supportedWidths.toString(),
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

    fun isHardwareAccelerated(info: MediaCodecInfo): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isHardwareAcceleratedQOrHigher(info)
        } else !isSoftwareOnly(info)
    }

    @TargetApi(29)
    private fun isHardwareAcceleratedQOrHigher(codecInfo: MediaCodecInfo): Boolean {
        return codecInfo.isHardwareAccelerated
    }

    private val SOFTWARE_IMPLEMENTATION_PREFIXES = arrayOf(
        "OMX.google.", "OMX.SEC.", "c2.android"
    )
    fun isSoftwareOnly(codecInfo: MediaCodecInfo): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return isSoftwareOnlyQOrHigher(codecInfo)
        }
        val name = codecInfo.name
        for (prefix in SOFTWARE_IMPLEMENTATION_PREFIXES) {
            if (name.startsWith(prefix!!)) {
                return true
            }
        }
        return false
    }

    @TargetApi(29)
    private fun isSoftwareOnlyQOrHigher(codecInfo: MediaCodecInfo): Boolean {
        return codecInfo.isSoftwareOnly
    }
    var value: String = ""

    private fun supportedSampleRateRanges(codecInfo: MediaCodecInfo): String{
        for (i in codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.supportedSampleRateRanges){
            value += i
        }
        return value
    }

}



