package com.example.network

import android.media.MediaDrm
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.GridView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.view.DisplayCompat
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
        var array1: ArrayList<String> = ArrayList<String>()
        var value1:String=""
        var value: String =""
        val displayManager = DisplayManagerCompat.getInstance(this)
        val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
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


        var i:Int=-0
        val xpi=displayMetrics.densityDpi
        fun hdr(): String {
            Log.i("pradnya hdr1", hdr.toString())
            if (hdr.equals(true)) {

                for (i in hdrType) {
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
            } else if (hdr.equals(false)) {
                array1.add("Not Supported")

            }
            for (i in array1) {
                value1 += i
                Log.i("pradnya hdr", i.toString())
            }
            return value1
        }

        fun smallestWidth(width:Int,height:Int): Int {
        if(width<height){
            return width/(displayMetrics.densityDpi/160)
        }
        else{
            return height/(displayMetrics.densityDpi/160)
        }
        }
        fun isUhdDevice(height: Int, width: Int):Boolean {

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
        //val wideColor=windowManager.defaultDisplay.isWideColorGamut
//      var device=windowManager.defaultDisplay.preferredWideGamutColorSpace
        var ratioWidth:Int=0
        var ratioHeigth:Int=0
        fun gcd(p:Int,q:Int): Int {
            if (q == 0)
                return p;
            else
                return gcd(q, p % q);
        }
        var ration:String =""
        fun ratio(a:Int,b:Int): String {
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
        fun displayModes(width:Int,height:Int): String {
            val alternativerefreshrate = windowManager.defaultDisplay.supportedModes

            for (i in alternativerefreshrate) {
                value += width.toString() + " X " + height.toString() + "@" + i.refreshRate.toInt() + "Hz \n"
                Log.i("pradnya", i.toString())
            }
            return value
        }
        var fhd:String=""
        fun display(width:Int,height:Int): String {
            var reso:String=""
            if(((height>=1920 && width>=1080) && (height<2560 && width<1440)) || ((width>=1920 && height>=1080)) && (width<2560 && height<1440)){
                reso="Full HD+"
            }
            if((height>=3840 && width>=2160) || (width>=3840 && height>=2160)){
                reso="4K"
            }
            if(((height>=1280 && width>=720) && (height<1920 && width<1080)) || (width>=1280 && height>=720) && (height<1920 && width<1080)){
                reso="HD"
            }
            if(((height>=2560 && width>=1440) && (height<3840 && width<2160)) || ((width>=2560 && height>=1440) && (width<3840 && height<2160))){
                reso="2k/QHD"
            }
            if((height<=720 && width<=720) || (width<=720 && height<=720)){
                reso="Bad Display"
            }

            return reso
        }
        fun getDeviceResolution(width:Int,height:Int): String {
            val modeCompatSupported = defaultDisplay?.let {
                DisplayCompat.getSupportedModes(this, it)
            }
            var data=""
            var size=0

            if (modeCompatSupported != null) {
                for (modeCompat in modeCompatSupported) {
                    Log.i("DisplaySize" ,"available"+ modeCompat.physicalWidth.toString() +" X " + modeCompat.physicalHeight.toString())
                    if(size < modeCompat.physicalWidth) {
                        size=modeCompat.physicalWidth
                        data= modeCompat.physicalWidth.toString() +" X " + modeCompat.physicalHeight.toString()
                        Log.i("DisplaySize" ,data)
                        ratioWidth=modeCompat.physicalWidth
                        ratioHeigth=modeCompat.physicalHeight
                    }
                }
                ration = ratio(ratioWidth,ratioHeigth)
                value=displayModes(ratioWidth,ratioHeigth)
                fhd=display(ratioWidth,ratioHeigth)
            }
            return data
        }

        val resolution=getDeviceResolution(width,height)

        var udh :Boolean = isUhdDevice(ratioHeigth,ratioWidth)

        dataModelArrayList.add(DataModel("Device Name", brand))
        dataModelArrayList.add(DataModel("Resolution", "$resolution\n$fhd"))
        dataModelArrayList.add(DataModel("Smallest Width", "${smallestWidth(width,height)}dp"))
        //dataModelArrayList.add(DataModel("HDR capabilities", hdrCapabilities.toString()))
        dataModelArrayList.add(DataModel("Refresh Rate", "$refreshRate Hz"))
        dataModelArrayList.add(DataModel("4k Support",udh.toString()))
        dataModelArrayList.add(DataModel("Rotation", rotation.toString()))
        //dataModelArrayList.add(DataModel("Density", density))
        dataModelArrayList.add(DataModel("Supported Display Modes",value))
//        4k, hevc support
        dataModelArrayList.add(DataModel("Luminance", "Max:$maxLum nits\nMin:$minLum nits"))
        dataModelArrayList.add(DataModel("HDR", hdr()))
        dataModelArrayList.add(DataModel("Pixels Per Inch", "X: $x ppi\nY: $y ppi"))
        dataModelArrayList.add(DataModel("DPI", "xhdpi: $xpi"))
        dataModelArrayList.add(DataModel("Ratio", ration))
       // dataModelArrayList.add(DataModel("Wide Color Gamut", "Display: $wideColor\nDevice: $device"))


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
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            hdcp = mediaDrm.connectedHdcpLevel.toString()
//        }

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
        //drmdataModelArrayList.add(DataModel("hdcp", hdcp.toString()))

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