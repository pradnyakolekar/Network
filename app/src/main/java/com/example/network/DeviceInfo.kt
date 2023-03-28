package com.example.network

import android.media.MediaDrm
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.DisplayMetrics
import android.widget.GridView
import java.util.*
import kotlin.collections.ArrayList

class DeviceInfo : AppCompatActivity() {

    private lateinit var idGrid: GridView
    private lateinit var id :String
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_deviceinfo)

        idGrid = findViewById(R.id.idGRV)
        val dataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val drmdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val codecdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()

        val bundle = intent.extras
        if (bundle != null) {
            id = "${bundle.getString("id")}"
        }

        //display info
        val display = windowManager.defaultDisplay
        val displayId = windowManager.defaultDisplay.displayId
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val refreshRate = windowManager.defaultDisplay.refreshRate
        val hdrCapabilities = windowManager.defaultDisplay.hdrCapabilities
        val rotation = windowManager.defaultDisplay.rotation
        val brand = Build.BRAND

        dataModelArrayList.add(DataModel("Device Name", brand))
        dataModelArrayList.add(DataModel("Ratio", "$width X $height"))
        dataModelArrayList.add(DataModel("Smallest Width", width.toString()))
        dataModelArrayList.add(DataModel("Refresh Rate", refreshRate.toString()))
        dataModelArrayList.add(DataModel("HDR capabilities", hdrCapabilities.toString()))
        dataModelArrayList.add(DataModel("Rotation", rotation.toString()))
        //dataModelArrayList.add(DataModel("Density", density))


        //drm info
        val WIDEVINE_UUID = UUID(-0x121074568629b532L,-0x5c37d8232ae2de13L)
        val mediaDrm = MediaDrm(WIDEVINE_UUID)
        val vendor = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VENDOR)
        val version = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VERSION)
        val description = mediaDrm.getPropertyString(MediaDrm.PROPERTY_DESCRIPTION)
        val algorithms = mediaDrm.getPropertyString(MediaDrm.PROPERTY_ALGORITHMS)
        //val securityLevel=mediaDrm.getPropertyString("SecurityLevel")
        var hdcp: String? = null
        val securityLevel = mediaDrm.getPropertyString("securityLevel")
        val systemId = mediaDrm.getPropertyString("systemId")
        val hdcpLevel = mediaDrm.getPropertyString("hdcpLevel")
        val maxHdcpLevel = mediaDrm.getPropertyString("maxHdcpLevel")
        val usageReportingSupport = mediaDrm.getPropertyString("usageReportingSupport")
        val maxNumberOfSessions = mediaDrm.getPropertyString("maxNumberOfSessions")
        val numberOfOpenSessions = mediaDrm.getPropertyString("numberOfOpenSessions")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            hdcp = mediaDrm.connectedHdcpLevel.toString()
        }

        drmdataModelArrayList.add(DataModel("Vendor", vendor))
        drmdataModelArrayList.add(DataModel("Version", version))
        drmdataModelArrayList.add(DataModel("Description", description))
        drmdataModelArrayList.add(DataModel("Algorithms", algorithms))
        drmdataModelArrayList.add(DataModel("Security Level", securityLevel))
        drmdataModelArrayList.add(DataModel("System Id", systemId))
        drmdataModelArrayList.add(DataModel("HDCP Level", hdcpLevel))
        drmdataModelArrayList.add(DataModel("MaxHdcp Level", maxHdcpLevel))
        drmdataModelArrayList.add(DataModel("usageReportingSupport", usageReportingSupport))
        drmdataModelArrayList.add(DataModel("maxNumberOfSessions", maxNumberOfSessions))
        drmdataModelArrayList.add(DataModel("numberOfOpenSessions", numberOfOpenSessions))
        drmdataModelArrayList.add(DataModel("hdcp", hdcp.toString()))

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