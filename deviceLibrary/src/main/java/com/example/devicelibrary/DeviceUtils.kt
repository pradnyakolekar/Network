package com.example.devicelibrary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.*
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.view.DisplayCompat
import okhttp3.*
import java.net.*
import java.util.*
import android.telephony.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import java.math.BigInteger

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
    var array1: ArrayList<String> = ArrayList<String>()
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


    fun manufacturerName(): String {
        val brand = Build.BRAND
        return "${brand.capitalize()}"
    }

    fun modelName(): String {
        val model = Build.MODEL
        return "$model"
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

    fun isUhdDevice(): String {
        var display=""
        var device=""
        val displayMetrics = DisplayMetrics()
        defaultDisplay?.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        val density = displayMetrics.densityDpi
        val diagonalPixels = Math.sqrt(Math.pow(width.toDouble(), 2.0) + Math.pow(height.toDouble(), 2.0))
        val screenSize = diagonalPixels / (density.toDouble() / DisplayMetrics.DENSITY_DEFAULT)
        if (ratioHeigth >= 2160 && ratioWidth >= 3840) {
            display= "Display: True"
        }
        else{
            display= "Display: False"
        }

        if (screenSize >= 27.0) {
            device= "Device: True"
        }
        else{
            device= "Device: False"
        }
        return "$display\n$device"

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

    fun hdr(): String {

        if (hdr?.equals(true)!!) {

            for (i in hdrType!!) {
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
                    array1.add("HDR 10 PLUS")
                }

            }
        } else if (hdr!!.equals(false)) {
            array1.add("Not Supported")

        }
        for (i in array1) {
            value1 += i
            Log.i("hdr values", i.toString())
        }
        return value1
    }

    @SuppressLint("ServiceCast")
    fun ipAddress(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "null"
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return "null"
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> getMobileIPAddressString().toString()
            else -> "null"
        }
    }


    fun getNetworkType(context: Context): String {
        val mConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val mInfo = mConnectivityManager.activeNetworkInfo
        if (mInfo == null || !mInfo.isConnected) return "-"
        if (mInfo.type == ConnectivityManager.TYPE_WIFI) return "WIFI"
        if (mInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return when (mInfo.subtype) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_GSM -> "2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_TD_SCDMA -> "3G"
                TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN, 19 -> "4G"
                TelephonyManager.NETWORK_TYPE_NR -> "5G"
                else -> "?"
            }
        }
        return "?"
    }



    fun getCurrentNetworkBandwidth(context: Context): String {
// Get the ConnectivityManager
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

// Get the active network
        val network = connectivityManager.activeNetwork ?: return "false"

// Get the network capabilities
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return "false"

// Get the download speed in Mbps
        val downloadSpeedMbps = networkCapabilities.linkDownstreamBandwidthKbps / (1000.0)

// Get the upload speed in Mbps
        val uploadSpeedMbps = networkCapabilities.linkUpstreamBandwidthKbps / (1000.0)

        // Print the results
        return String.format("Download: %.2f Mbps\n Upload: %.2f Mbps".format(downloadSpeedMbps, uploadSpeedMbps))
    }


    //Generate normal IP address
    fun getIPAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val addresses = networkInterface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()
                    if (!address.isLinkLocalAddress && !address.isLoopbackAddress && address is InetAddress) {
                        return address.hostAddress
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }


    //ipv4 and ipv6 address
    fun getMobileIPAddress(): Pair<List<String>, List<String>> {
        val ipv4Addresses = mutableListOf<String>()
        val ipv6Addresses = mutableListOf<String>()
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val inetAddresses = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    val address = inetAddresses.nextElement()
                    if (!address.isLinkLocalAddress && !address.isLoopbackAddress) {
                        if (address is Inet4Address) {
                            ipv4Addresses.add(address.hostAddress)
                        } else if (address is Inet6Address) {
                            ipv6Addresses.add(address.hostAddress)
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Pair(ipv4Addresses, ipv6Addresses)
    }
    fun getMobileIPAddressString(): Pair<String, String> {
        val (ipv4Addresses, ipv6Addresses) = getMobileIPAddress()
        val ipv4String = ipv4Addresses.joinToString(separator = ",\n")
        val ipv6String = ipv6Addresses.joinToString(separator = ",\n")
        return Pair("ipv4:$ipv4String\n","ipv6:$ipv6String")
    }

    fun getNetworkSpeed1(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var downSpeed=""
        var upSpeed=""
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            downSpeed = (nc?.linkDownstreamBandwidthKbps)?.div(1000).toString()
            upSpeed = (nc?.linkUpstreamBandwidthKbps)?.div(1000).toString()
            return "Download: $downSpeed\nUpload: $upSpeed"
        }
        else{
            "-"
        }


    }

fun connectivity(context: Context): String {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo

    if (networkInfo != null && networkInfo.isConnected) {
        try {
            val url = URL("https://www.google.com")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 5000 // Set timeout to 5 seconds
            val statusCode = urlConnection.responseCode

            if (statusCode == 200) {
                return "Internet is available"
            } else {
                return "Internet is not available"
            }
        } catch (e: Exception) {
            return "Internet is not available"
        }
    } else {
        return "No network connection is not available"
    }

}

    fun getNetworkOperatorName(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val operator= telephonyManager.networkOperatorName
        if(operator.isNotEmpty()){
            return operator
        }
        else{
            return "Wifi Vendor"
        }
    }

    fun dhcp(context: Context): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        val dhcpServerAddress = Formatter.formatIpAddress(dhcpInfo.serverAddress)
        if(dhcpServerAddress.equals("0.0.0.0")) {
            return "Not connected to Wifi"
        }
        else{
            return dhcpServerAddress
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getMobileSignalStrength(context: Context): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val signalStrength = telephonyManager.signalStrength
        if (signalStrength != null) {
            return "${signalStrength.cellSignalStrengths.get(0).dbm} dBm"
        }
        return "Only Wifi supported"
    }


    fun rssi(context: Context):String{
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val rssi = wifiInfo.rssi
        return "$rssi dBm"
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun strength(context: Context):String{
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled) {
            val wifiInfo = wifiManager.connectionInfo
            val rssi = wifiInfo.rssi
            return "$rssi dBm (WiFi)"
        } else {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val signalStrength = telephonyManager.signalStrength
            if (signalStrength != null) {
                return "${signalStrength.cellSignalStrengths.get(0).dbm} dBm (Cellular)"

            }
        }
        return "Unknown"
    }

    fun getNetworkBandwidth(connectivityManager: ConnectivityManager): Long {
        val activeNetwork: Network? = connectivityManager.activeNetwork
        val networkCapabilities: NetworkCapabilities? =
            connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.linkDownstreamBandwidthKbps?.toLong() ?: 0L
    }

    fun bandwidth(context: Context):String{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val bandwidth = getNetworkBandwidth(connectivityManager)
        return "$bandwidth"

    }
}