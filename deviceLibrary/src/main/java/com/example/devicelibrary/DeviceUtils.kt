package com.example.devicelibrary

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Display
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.view.DisplayCompat

class DeviceUtils(context: Context) {
    val displayManager = DisplayManagerCompat.getInstance(context)
    val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
    val modeCompatSupported = defaultDisplay?.let {
        DisplayCompat.getSupportedModes(context, it)
    }
    var ratioWidth: Int = 0
    var ratioHeigth: Int = 0
    var result: String = ""
    var value1: String = ""
    var value: String = ""
    val displayMetrics = context.resources.displayMetrics
    val hdrCapabilities = defaultDisplay?.hdrCapabilities
    val hdr = defaultDisplay?.isHdr
    val hdrType = hdrCapabilities?.supportedHdrTypes

    fun xppi(): Int {
        return displayMetrics.xdpi.toInt()
    }

    fun yppi(): Int {
        return displayMetrics.xdpi.toInt()
    }

    fun maxLum(): Int? {
        return hdrCapabilities?.desiredMaxLuminance?.toInt()
    }

    fun minLum(): Int? {
        return hdrCapabilities?.desiredMinLuminance?.toInt()
    }

    fun rotation(): String {
        return defaultDisplay?.rotation.toString()
    }

    fun refreshRate(): Int {
        return defaultDisplay?.refreshRate?.toInt() ?: 0
    }


    fun deviceName(): String {
        val brand = Build.BRAND
        return brand
    }

    fun displayModes(): String {
        val alternativerefreshrate = defaultDisplay?.supportedModes

        if (alternativerefreshrate != null) {
            for (i in alternativerefreshrate) {
                value += ratioWidth.toString() + " X " + ratioHeigth.toString() + "@" + i.refreshRate.toInt() + "Hz \n"
            }
        }
        return value
    }

    fun display(): String {

        if (((ratioHeigth >= 1920 && ratioWidth >= 1080) && (ratioHeigth < 2560 && ratioWidth < 1440)) || ((ratioWidth >= 1920 && ratioHeigth >= 1080)) && (ratioWidth < 2560 && ratioHeigth < 1440)) {
            return "Full HD+"
        }
        if ((ratioHeigth >= 3840 && ratioWidth >= 2160) || (ratioWidth >= 3840 && ratioHeigth >= 2160)) {
            return "4K"
        }
        if (((ratioHeigth >= 1280 && ratioWidth >= 720) && (ratioHeigth < 1920 && ratioWidth < 1080)) || (ratioWidth >= 1280 && ratioHeigth >= 720) && (ratioHeigth < 1920 && ratioWidth < 1080)) {
            return "HD"
        }
        if (((ratioHeigth >= 2560 && ratioWidth >= 1440) && (ratioHeigth < 3840 && ratioWidth < 2160)) || ((ratioWidth >= 2560 && ratioHeigth >= 1440) && (ratioWidth < 3840 && ratioHeigth < 2160))) {
            return "2k/QHD"
        }
        if ((ratioHeigth <= 720 && ratioWidth <= 720) || (ratioWidth <= 720 && ratioHeigth <= 720)) {
            return "Bad Display"
        }

        return "false"
    }

    fun gcd(p: Int, q: Int): Int {
        if (q == 0)
            return p;
        else
            return gcd(q, p % q);
    }

    fun ratio(ratioWidth: Int, ratioHeigth: Int) {
        var gcd = gcd(ratioWidth, ratioHeigth)
        if (ratioWidth > ratioHeigth) {
            var x = ratioWidth / gcd
            var y = ratioHeigth / gcd
            result = "$x:$y"
        } else {
            var x = ratioHeigth / gcd
            var y = ratioWidth / gcd
            result = "$x:$y"
        }
    }

    fun deviceRatio(): String {
        ratio(ratioWidth, ratioHeigth)
        return result
    }

    fun getDeviceResolution(): String {
        var data = ""
        var size = 0
        if (modeCompatSupported != null) {
            for (modeCompat in modeCompatSupported) {
                Log.i(
                    "DisplaySize",
                    "available" + modeCompat.physicalWidth.toString() + " X " + modeCompat.physicalHeight.toString()
                )
                if (size < modeCompat.physicalWidth) {
                    size = modeCompat.physicalWidth
                    data =
                        modeCompat.physicalWidth.toString() + " X " + modeCompat.physicalHeight.toString()
                    Log.i("DisplaySize", data)
                    ratioWidth = modeCompat.physicalWidth
                    ratioHeigth = modeCompat.physicalHeight
                }
            }
        }
        return data
    }

    fun isUhdDevice(): Boolean {
        if (ratioHeigth >= 2160 && ratioWidth >= 3840) {
            return true
        }
        return false
    }

    fun smallestWidth(): Int {
        if (ratioWidth < ratioHeigth) {
            return ratioWidth / (displayMetrics.densityDpi / 160)
        } else {
            return ratioHeigth / (displayMetrics.densityDpi / 160)
        }
    }

    fun xdpi(): Int {
        return displayMetrics.densityDpi
    }

}