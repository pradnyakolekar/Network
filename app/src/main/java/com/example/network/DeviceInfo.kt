package com.example.network

import android.content.Context
import android.content.res.Configuration
import android.media.MediaDrm
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.hardware.display.DisplayManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.GridView
import java.util.*
import kotlin.collections.ArrayList

class DeviceInfo : AppCompatActivity() {

    private lateinit var idGrid: GridView
    private lateinit var id :String
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_deviceinfo)

        idGrid = findViewById(R.id.idGRV)
        val dataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val drmdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        val codecdataModelArrayList: ArrayList<DataModel> = ArrayList<DataModel>()
        var array: ArrayList<String> = ArrayList<String>()
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
        val x=displayMetrics.xdpi.toInt()
        val y=displayMetrics.ydpi.toInt()
        val refreshRate = windowManager.defaultDisplay.refreshRate.toInt()
        val hdrCapabilities = windowManager.defaultDisplay.hdrCapabilities
        val rotation = windowManager.defaultDisplay.rotation
        val brand = Build.BRAND
        val maxLum=hdrCapabilities.desiredMaxLuminance.toInt()
        val minLum=hdrCapabilities.desiredMinLuminance.toInt()
        val hdr=windowManager.defaultDisplay.isHdr
        val hdrType=hdrCapabilities.supportedHdrTypes

        val xpi=displayMetrics.densityDpi

        fun isUhdDevice(height:Int,width:Int) : Boolean {

            //val displayManager = DisplayManagerCompat.getInstance(context)
            //val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)


            //defaultDisplay?.let { display ->
                 if(height >= 2160 && width >= 3840){
                     return true
                }
           // }
            return false
        }
        //val ratio=(height/width).toString()
        val wideColor=windowManager.defaultDisplay.isWideColorGamut
        var device=windowManager.defaultDisplay.preferredWideGamutColorSpace

        fun gcd(p:Int,q:Int): Int {
            if (q == 0)
                return p;
            else
                return gcd(q, p % q);
        }
        fun ratio(a:Int,b:Int) : String{
             var gcd = gcd(a,b)
            if(a > b) {
                var x= a/gcd
                var y = b/gcd
                return "$x:$y"
            } else {
                var x= b/gcd
                var y = a/gcd
                return "$x:$y"
            }
        }
        var udh :Boolean = isUhdDevice(height,width)
        var ration :String= ratio(width,height)

        dataModelArrayList.add(DataModel("Device Name", brand))
        dataModelArrayList.add(DataModel("Ratio", "$width X $height"))
        dataModelArrayList.add(DataModel("Smallest Width", width.toString()))
        dataModelArrayList.add(DataModel("Refresh Rate", "$refreshRate Hz"))
        dataModelArrayList.add(DataModel("4k Support",udh.toString()))
        dataModelArrayList.add(DataModel("Rotation", rotation.toString()))
        dataModelArrayList.add(DataModel("Luminance", "Max:$maxLum nits\nMin:$minLum nits"))
        dataModelArrayList.add(DataModel("HDR", "$hdr"))
        dataModelArrayList.add(DataModel("Pixels Per Inch", "X: $x ppi\nY: $y ppi"))
        dataModelArrayList.add(DataModel("DPI", "xhdpi: $xpi"))
        dataModelArrayList.add(DataModel("Ratio", ration))
        dataModelArrayList.add(DataModel("Wide Color Gamut", "Display: $wideColor\nDevice: $device"))


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