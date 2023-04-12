package com.example.devicelibrary

import android.content.Context
import android.media.MediaDrm
import java.util.*

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
}