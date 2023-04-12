package com.example.network

import android.annotation.SuppressLint
import android.media.MediaDrm
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.devicelibrary.*
import com.google.android.exoplayer2.C
import java.util.*
import kotlin.collections.ArrayList

class DeviceInfo : AppCompatActivity() {

    private lateinit var idGrid: GridView
    private lateinit var id: String


    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_deviceinfo)
        idGrid = findViewById(R.id.idGRV)

        val bundle = intent.extras
        if (bundle != null) {
            id = "${bundle.getString("id")}"
        }


        val device = DeviceUtils(applicationContext)
        val mediaDrm:MediaDrm = MediaDrm(C.WIDEVINE_UUID)
        val drm=DrmUtils(mediaDrm)
        val dataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val drmdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        var array: ArrayList<String> = ArrayList<String>()
        var array1: ArrayList<String> = ArrayList<String>()


        // Pass the WindowManager object to another class


        //val refreshRate =windowManager.defaultDisplay.refreshRate.toInt()
        fun hdr(): String {
            Log.i("hdr", device.hdr.toString())
            if (device.hdr?.equals(true)!!) {

                for (i in device.hdrType!!) {
                    Log.i("HDR Type", i.toString())
                    if (i.equals(1)) {
                        array1.add("HDR DOLBY VISION\n")
                    }
                    if (i.equals(2)) {
                        array1.add("HDR 10\n")
                    }
                    if (i.equals(3)) {
                        array1.add("HDR HLG\n")
                    }
                    if (i.equals(4)) {
                        array1.add("HDR 10 PLUS\n")
                    }

                }
            } else if (device.hdr!!.equals(false)) {
                array1.add("Not Supported")

            }
            for (i in array1) {
                device.value1 += i
                Log.i("hdr values", i.toString())
            }
            return device.value1
        }


        //device info
        dataModelArrayList.add(DataModel("Device Name", device.deviceName()))
        dataModelArrayList.add(DataModel("Resolution", "${device.getDeviceResolution()}\n ${device.display()}"))
        dataModelArrayList.add(DataModel("Smallest Width", "${device.smallestWidth()} dp"))
        dataModelArrayList.add(DataModel("Refresh Rate", "${device.refreshRate()}  Hz"))
        dataModelArrayList.add(DataModel("4k Support", device.isUhdDevice().toString()))
        dataModelArrayList.add(DataModel("Rotation", device.rotation()))
        dataModelArrayList.add(DataModel("Supported Display Modes", device.displayModes()))
        dataModelArrayList.add(
            DataModel(
                "Luminance",
                "Max:${device.maxLum()} nits\nMin:${device.minLum()} nits"
            )
        )
        dataModelArrayList.add(DataModel("HDR", hdr()))
        dataModelArrayList.add(
            DataModel(
                "Pixels Per Inch",
                "X: ${device.xppi()} ppi\nY: ${device.yppi()} ppi"
            )
        )
        dataModelArrayList.add(DataModel("DPI", "xhdpi: ${device.xdpi()}"))
        dataModelArrayList.add(DataModel("Ratio", device.deviceRatio()))

        //drm info
        drmdataModelArrayList.add(DataModel("Vendor", drm.vendor()))
        drmdataModelArrayList.add(DataModel("Version", drm.version()))
        drmdataModelArrayList.add(DataModel("Description", drm.description()))
        drmdataModelArrayList.add(DataModel("Algorithms", drm.algorithms()))
        drmdataModelArrayList.add(DataModel("Security Level", drm.securityLevel()))
        drmdataModelArrayList.add(DataModel("System Id", drm.systemId()))
        drmdataModelArrayList.add(DataModel("HDCP Level", drm.hdcpLevel()))
        drmdataModelArrayList.add(DataModel("MaxHdcp Level", drm.maxHdcpLevel()))
        drmdataModelArrayList.add(DataModel("usageReportingSupport", drm.usageReportingSupport()))
        drmdataModelArrayList.add(DataModel("maxNumberOfSessions", drm.maxNumberOfSessions()))
        drmdataModelArrayList.add(DataModel("numberOfOpenSessions", drm.numberOfOpenSessions()))



        if (id == "display") {
            val adapter = DisplayAdapter(this, dataModelArrayList)
            idGrid.adapter = adapter
        } else if (id == "drm") {
            val adapter = DisplayAdapter(this, drmdataModelArrayList)
            idGrid.adapter = adapter
        } else {
            val adapter = DisplayAdapter(this, drmdataModelArrayList)
            idGrid.adapter = adapter
        }
    }
}