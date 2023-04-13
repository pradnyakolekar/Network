package com.example.codeclibrary

import android.annotation.TargetApi
import android.media.MediaCodecInfo
import android.media.MediaCodecInfo.CodecProfileLevel
import android.media.MediaCodecInfo.EncoderCapabilities.*
import android.media.MediaCodecList
import android.os.Build

class codecUtils {

    var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos

    fun name(codecInfo: MediaCodecInfo): String {
        return codecInfo.name
    }

    fun supportedTypes(codecInfo: MediaCodecInfo): String {
        return codecInfo.supportedTypes[0]
    }

    fun isHardwareAccelerated(info: MediaCodecInfo): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isHardwareAcceleratedQOrHigher(info)
        } else !isSoftwareOnly(info)
    }

    @TargetApi(29)
    private fun isHardwareAcceleratedQOrHigher(codecInfo: MediaCodecInfo): Boolean {
        return codecInfo.isHardwareAccelerated
    }

    private val SOFTWARE_IMPLEMENTATION_PREFIXES = arrayOf(
        "OMX.google.", "OMX.SEC.", "c2.android"
    )

    fun isSoftwareOnly(codecInfo: MediaCodecInfo): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return isSoftwareOnlyQOrHigher(codecInfo)
        }
        val name = codecInfo.name
        for (prefix in SOFTWARE_IMPLEMENTATION_PREFIXES) {
            if (name.startsWith(prefix!!)) {
                return true
            }
        }
        return false
    }

    @TargetApi(29)
    private fun isSoftwareOnlyQOrHigher(codecInfo: MediaCodecInfo): Boolean {
        return codecInfo.isSoftwareOnly
    }

    fun maxSupportedInstances(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).maxSupportedInstances.toString()
    }

    fun lowLantency(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val lowLatency =
            capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_LowLatency)
        return lowLatency.toString()
    }

    fun multipleAccessFrames(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val multipleaccessFrame =
            capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_MultipleFrames)
        return multipleaccessFrame.toString()
    }

    fun tunneledPlayback(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val tunneledPlayback =
            capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_TunneledPlayback)
        return tunneledPlayback.toString()
    }

    fun partialAUperIB(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val partial = capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_PartialFrame)
        return partial.toString()
    }


//audio

    fun bitrateRange(codecInfo: MediaCodecInfo): String {
    var lower = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.bitrateRange.lower
    var upper = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.bitrateRange.upper
        lower /= 1000
        upper /= 1000
//    when {
//        lower < 1000 -> lower = ("$lower bps")
//        maxBitrate < 1000000 -> ("${maxBitrate / 1000} kbps")
//        maxBitrate < 1000000000 -> ("${maxBitrate / 1000000} Mbps")
//        else -> ("${maxBitrate / 1000000000} Gbps")
//    }
    return "$lower Kbps - $upper Kbps"
    }

    fun inputChannelCount(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.maxInputChannelCount.toString()
    }

    @TargetApi(29)
    fun dynamicTimestamp(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val supportsDynamicTimestamps =
            capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_DynamicTimestamp)
        return supportsDynamicTimestamps.toString()
    }

    var value: String = ""
    fun supportedSampleRateRanges(codecInfo: MediaCodecInfo): String {
        for (i in codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.supportedSampleRateRanges) {
            value += i
        }
        return value
    }

    fun sampleRates(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.supportedSampleRates.toString()
    }

//video

    fun maxBitrate(codecInfo: MediaCodecInfo): String {
        val maxBitrate = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.bitrateRange.upper
        return when {
            maxBitrate < 1000 -> ("$maxBitrate bps")
            maxBitrate < 1000000 -> ("${maxBitrate / 1000} kbps")
            maxBitrate < 1000000000 -> ("${maxBitrate / 1000000} Mbps")
            else -> ("${maxBitrate / 1000000000} Gbps")
        }
    }

    fun supportedFrameRates(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.supportedFrameRates.toString()
    }

    @TargetApi(29)
    fun supportedPerformancePoints(codecInfo: MediaCodecInfo): String {
        //return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.supportedPerformancePoints.toString()
        return "null"
    }

    fun supportedWidths(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.supportedWidths.toString()
    }

    fun getSupportedColorFormats(mimeType: String): List<Int> {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        val infos = codecList.getCodecInfos()
        for (info in infos) {
            if (!info.isEncoder && info.supportedTypes.contains(mimeType)) {
                val formats = info.getCapabilitiesForType(mimeType).colorFormats
                for (field in MediaCodecInfo.CodecCapabilities::class.java.declaredFields) {
                    if (field.type.toString() == "int") {
                        val name = field.get(MediaCodecInfo.CodecCapabilities()) as Int

                        if (name == formats[0] ) {
                            formats[0] ==name
                            break
                        }
                    }
                }
                return formats.asList()
            }
        }
        return emptyList()
    }

    fun getSupportedBitrateModes(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        value = codecInfo.name.lowercase()
        if(value.contains("encoder")){
            value = "Constant bitrate (CBR): ${capabilities.encoderCapabilities.isBitrateModeSupported(BITRATE_MODE_CBR)} \n"
            value += "Constant Quality (CQ): ${capabilities.encoderCapabilities.isBitrateModeSupported(BITRATE_MODE_CQ) } \n"
            value += "Variable bitrate (VBR): ${capabilities.encoderCapabilities.isBitrateModeSupported(BITRATE_MODE_VBR)} \n"
            value += "Constant bitrate (CBR): ${capabilities.encoderCapabilities.isBitrateModeSupported(BITRATE_MODE_CBR_FD)} \n"
            return value
        } else {
            return "null"
        }
    }


    fun adaptivePlayback(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        value = capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_AdaptivePlayback).toString()
        return value
    }

    fun securePlayback(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        value= capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_SecurePlayback).toString()
        return value
    }

    fun infraRefresh(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        value = capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_IntraRefresh).toString()
        return value
    }

    fun checkProfileLevels(codecInfo: MediaCodecInfo) : String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val levels = capabilities.profileLevels
        for (level in levels) {
           // value += (" ${level.profile}/${level.level} \n")
            for (field in CodecProfileLevel::class.java.declaredFields) {
                if (field.type.toString() == "int") {
                    val name = field.get(CodecProfileLevel()) as Int
                    if (name == level.level ) {
                        value += (" ${level.profile}/${field.name} \n")
                        break
                    }
                }
            }
        }

        return value
    }
}

