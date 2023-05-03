package com.example.network

import android.annotation.SuppressLint
import android.media.MediaDrm
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.devicelibrary.*
import com.google.android.exoplayer2.C
import java.util.*
import kotlin.collections.ArrayList

class DeviceInfo : AppCompatActivity() {

    private lateinit var idGrid: RecyclerView
    private lateinit var recyclerViewAdapter: DisplayAdapter
    private lateinit var id: String


    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_deviceinfo)
        idGrid = findViewById<RecyclerView>(R.id.idGRV)

        val bundle = intent.extras
        if (bundle != null) {
            id = "${bundle.getString("id")}"
        }

        val device = DeviceUtils(applicationContext)
        val mediaDrm:MediaDrm = MediaDrm(C.WIDEVINE_UUID)
        val drm=DrmUtils(mediaDrm)
        val dataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val drmdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val networkdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()

        //device info
        dataModelArrayList.add(DataModel("Manufacturer Name", device.manufacturerName()))
        dataModelArrayList.add(DataModel("Model Name", device.modelName()))
        dataModelArrayList.add(DataModel("Resolution", device.getDeviceResolution()))
        dataModelArrayList.add(DataModel("Resolution Type", device.display()))
        dataModelArrayList.add(DataModel("Smallest Width", "${device.smallestWidth()} dp"))
        dataModelArrayList.add(DataModel("Refresh Rate", "${device.refreshRate()}  Hz"))
        dataModelArrayList.add(DataModel("Rotation",device.rotation()))
        dataModelArrayList.add(DataModel("DPI", "xhdpi: ${device.xdpi()}"))
        dataModelArrayList.add(DataModel("Ratio", device.deviceRatio()))
        dataModelArrayList.add(DataModel("4k Support", device.isUhdDevice()))
        dataModelArrayList.add(DataModel("Display Modes", device.displayModes()))
        dataModelArrayList.add(DataModel("Luminance", "Max:${device.maxLum()} nits\nMin:${device.minLum()} nits"))
        dataModelArrayList.add(DataModel("Pixels Per Inch", "X: ${device.xppi()} ppi\nY: ${device.yppi()} ppi"))
        dataModelArrayList.add(DataModel("HDR", device.hdr()))


        //network info
        networkdataModelArrayList.add(DataModel("IP address", device.ipAddress(this).replace("(","").replace(")","")))
        networkdataModelArrayList.add(DataModel("Network Type", device.getNetworkType(this)))
        networkdataModelArrayList.add(DataModel("Bandwidth", device.getCurrentNetworkBandwidth(this)))
        networkdataModelArrayList.add(DataModel("Internet Availability", device.connectivity(this)))
        networkdataModelArrayList.add(DataModel("Operator", device.getNetworkOperatorName(this)))
        networkdataModelArrayList.add(DataModel("DHCP", device.dhcp(this)))
        networkdataModelArrayList.add(DataModel("Signal Strength", device.strength(this)))

        //drm info
        drmdataModelArrayList.add(DataModel("Vendor", drm.vendor()))
        drmdataModelArrayList.add(DataModel("Version", drm.version()))
        drmdataModelArrayList.add(DataModel("Description", drm.description()))
        drmdataModelArrayList.add(DataModel("Algorithms", drm.algorithms()))
        drmdataModelArrayList.add(DataModel("Security Level", drm.securityLevel()))
        drmdataModelArrayList.add(DataModel("System Id", drm.systemId()))
        drmdataModelArrayList.add(DataModel("HDCP Level", drm.hdcpLevel()))
        drmdataModelArrayList.add(DataModel("MaxHdcp Level", drm.maxHdcpLevel()))
        drmdataModelArrayList.add(DataModel("Usage Reporting", drm.usageReportingSupport()))
        drmdataModelArrayList.add(DataModel("Max Sessions", drm.maxNumberOfSessions()))
        drmdataModelArrayList.add(DataModel("Open Sessions", drm.numberOfOpenSessions()))
        drmdataModelArrayList.add(DataModel("Chipset", drm.chipset()))
        drmdataModelArrayList.add(DataModel("Ram Info", drm.ramInfo(this)))



        if (id == "display") {
            val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            idGrid.layoutManager = layoutManager
            recyclerViewAdapter = DisplayAdapter(this, dataModelArrayList)
            idGrid.adapter = recyclerViewAdapter
        } else if (id == "drm") {
            val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            idGrid.layoutManager = layoutManager
            recyclerViewAdapter = DisplayAdapter(this, drmdataModelArrayList)
            idGrid.adapter = recyclerViewAdapter
        }
        else if (id == "network") {
            val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            idGrid.layoutManager = layoutManager
            recyclerViewAdapter = DisplayAdapter(this, networkdataModelArrayList)
            idGrid.adapter = recyclerViewAdapter
        }else {
            val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            idGrid.layoutManager = layoutManager
            recyclerViewAdapter = DisplayAdapter(this, drmdataModelArrayList)
            idGrid.adapter = recyclerViewAdapter
        }
    }
}