package com.example.devicelibrary

import android.app.ActivityManager
import android.content.Context
import android.media.MediaDrm
import android.os.Build


class DrmUtils(mediaDrm:MediaDrm) {

    var mediaDrm:MediaDrm
    init {
        this.mediaDrm = mediaDrm
    }

    fun vendor(): String {
        return mediaDrm.getPropertyString(MediaDrm.PROPERTY_VENDOR)
    }

    fun version(): String {
        return mediaDrm.getPropertyString(MediaDrm.PROPERTY_VERSION)
    }

    fun description(): String {
        return mediaDrm.getPropertyString(MediaDrm.PROPERTY_DESCRIPTION)
    }

    fun algorithms(): String {
        return mediaDrm.getPropertyString(MediaDrm.PROPERTY_ALGORITHMS)
    }

    fun securityLevel(): String {
        return mediaDrm.getPropertyString("securityLevel")
    }

    fun systemId(): String {
        return mediaDrm.getPropertyString("systemId")
    }

    fun hdcpLevel(): String {
        return mediaDrm.getPropertyString("hdcpLevel")
    }

    fun maxHdcpLevel(): String {
        return mediaDrm.getPropertyString("maxHdcpLevel")
    }

    fun usageReportingSupport(): String {
        return mediaDrm.getPropertyString("usageReportingSupport")
    }

    fun maxNumberOfSessions(): String {
        return mediaDrm.getPropertyString("maxNumberOfSessions")
    }

    fun numberOfOpenSessions(): String {
        return mediaDrm.getPropertyString("numberOfOpenSessions")
    }
    fun chipset(): String {
        val chip=Build.HARDWARE
        return chip
    }

    fun ramInfo(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val totalRamBytes = memoryInfo.totalMem
        val totalRam = String.format("%.2f GB", totalRamBytes.toDouble() / (1024 * 1024 * 1024))
        val availableRamBytes = memoryInfo.availMem
        val availableRam = String.format("%.2f GB", availableRamBytes.toDouble() / (1024 * 1024 * 1024))
        return "Total Ram: $totalRam \nAvailable Ram: $availableRam "
    }
}