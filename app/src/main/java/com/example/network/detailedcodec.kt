package com.example.network

import DetailAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class detailedcodec: AppCompatActivity() {

    private val detaileddata: ArrayList<DataModel> = ArrayList<DataModel>()
    private lateinit var idGrid: GridView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail)
        idGrid = findViewById(R.id.rvdetail)

        val extras = intent.extras
        if (extras != null) {
            val SupportedTypes = extras.getString("name")
            val hwacc = extras.getString("hwacc")
            val swonly = extras.getString("swonly")
            val lowlat = extras.getString("lowlatency")
            val supportInstance = extras.getString("supportInstance")
            val bitRateRange = extras.getString("bitRateRange")
            val channelCount = extras.getString("channelCount")
            val profile = extras.getString("profile")
            val color = extras.getString("color")
            val dynamic = extras.getString("dynamic")
            val multiac = extras.getString("multiaccess")
            val tunneledF = extras.getString("tunneledframe")
            val maxReso = extras.getString("maxReso")
            val maxframe = extras.getString("maxframe")
            val frame = extras.getString("frame")
            val partialFrame = extras.getString("partailFrame")
            val bitrateModes = extras.getString("bitrateModes")
            val isHdr = extras.getString("isHDR")
            val adaptivePlayback = extras.getString("adaptivePlayback")
            val securePlayback = extras.getString("securePlayback")
            val infraRefresh = extras.getString("infraRefresh")

            if (SupportedTypes != null) {
                if (SupportedTypes.contains("audio")) {
                    detaileddata.add(DataModel("Supported Types", SupportedTypes))
                    detaileddata.add(DataModel("Hardware Acceleration", hwacc.toString()))
                    detaileddata.add(DataModel("Software Only", swonly.toString()))
                    detaileddata.add(DataModel("Low Latency", lowlat.toString()))
                    detaileddata.add(DataModel("Max Supported Instance", supportInstance.toString()))
                    detaileddata.add(DataModel("Bitrate Range", bitRateRange.toString()))
                    detaileddata.add(DataModel("Input Channel Count", channelCount.toString()))
                    detaileddata.add(DataModel("Dynamic Timestamp", dynamic.toString()))
                    detaileddata.add(DataModel("Multiple Access Frames", multiac.toString()))
                    detaileddata.add(DataModel("Tunneled playback", tunneledF.toString()))
                    detaileddata.add(DataModel("Partial Access Units per Input Buffer", partialFrame.toString()))
                } else {
                    detaileddata.add(DataModel("Supported Types", SupportedTypes))
                    detaileddata.add(DataModel("Hardware Acceleration", hwacc.toString()))
                    detaileddata.add(DataModel("Software Only", swonly.toString()))
                    detaileddata.add(DataModel("Low Latency", lowlat.toString()))
                    detaileddata.add(DataModel("Max Supported Instance", supportInstance.toString()))
                    detaileddata.add(DataModel("Max Bitrate", bitRateRange.toString()))
                    detaileddata.add(DataModel("Infra Refresh", infraRefresh.toString()))
                    detaileddata.add(DataModel("Max Resolution", maxReso.toString()))

                    detaileddata.add(DataModel("Frame Rate", frame.toString()))

                    detaileddata.add(DataModel("Color Formats", color.toString()))
                    detaileddata.add(DataModel("Dynamic Timestamp", dynamic.toString()))
                    detaileddata.add(DataModel("Supported Bitrate Modes", bitrateModes.toString()))
                    detaileddata.add(DataModel("HDR Editing", isHdr.toString()))
                    detaileddata.add(DataModel("Adaptive Playback", adaptivePlayback.toString()))
                    detaileddata.add(DataModel("Secure Playback", securePlayback.toString()))
                    detaileddata.add(DataModel("Multiple Access Frames", multiac.toString()))
                    detaileddata.add(DataModel("Tunneled playback", tunneledF.toString()))
                    detaileddata.add(DataModel("Partial Access Units per Input Buffer", partialFrame.toString()))
                    detaileddata.add(DataModel("Profile Levels", profile.toString()))
                    detaileddata.add(DataModel("Max Frame Rate per resolution", maxframe.toString()))
                }
            }
            }

        val adapter = DisplayAdapter(this, detaileddata)
        idGrid.adapter = adapter
    }
}