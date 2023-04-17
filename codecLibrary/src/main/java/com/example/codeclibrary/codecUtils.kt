package com.example.codeclibrary

import android.annotation.TargetApi
import android.content.Context
import android.media.MediaCodecInfo
import android.media.MediaCodecInfo.CodecProfileLevel
import android.media.MediaCodecInfo.EncoderCapabilities.*
import android.media.MediaCodecList
import android.os.Build

class codecUtils(context: Context) {

    var mediaCodecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
    var array1: ArrayList<String> = ArrayList<String>()
    var value1: String = ""

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

    fun partialAccessUnitperIB(codecInfo: MediaCodecInfo): String {
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val partial = capabilities.isFeatureSupported(MediaCodecInfo.CodecCapabilities.FEATURE_PartialFrame)
        return "false"
    }


//audio
    fun bitrateRange(codecInfo: MediaCodecInfo): String {
        var lower =
            codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.bitrateRange.lower
        var upper =
            codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).audioCapabilities.bitrateRange.upper
        return "${conversion(lower)} Kbps - ${conversion(upper)} Kbps"
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
        return conversion(maxBitrate)
    }

    fun supportedFrameRates(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.supportedFrameRates.toString()
    }

    @TargetApi(29)
    fun supportedPerformancePoints(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.supportedPerformancePoints.toString()
    }

    //error
    fun supportedWidths(codecInfo: MediaCodecInfo): String {
        return codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0]).videoCapabilities.supportedWidths.toString()
    }


    fun getSupportedColorFormats(mimeType: String): String {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        val infos = codecList.getCodecInfos()
        var value1 = ""
        for (info in infos) {
            if (!info.isEncoder && info.supportedTypes.contains(mimeType)) {
                val formats = info.getCapabilitiesForType(mimeType).colorFormats
                for (format in formats) {
                    for (field in MediaCodecInfo.CodecCapabilities::class.java.declaredFields) {
                        if (field.type.toString() == "int") {
                            val name = field.get(MediaCodecInfo.CodecCapabilities()) as Int
                            if (name == format) {
                                 value1 += "${field.name} (${format}) \n"
                                break
                            }
                        }
                    }
                }
                return value1
            }
        }
        return "null"
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

    //need to verify
    fun isHDReditsupports(codecInfo: MediaCodecInfo): String{
        val capabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        val profileLevels = capabilities.profileLevels
        if (profileLevels.any { it.profile == MediaCodecInfo.CodecProfileLevel.AVCProfileHigh && it.level >= MediaCodecInfo.CodecProfileLevel.AVCLevel31 }) {
            return "true"
        } else {
            return "false"
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
        var value1 = ""
        for (level in levels) {
            value1 += "${fieldS(level.profile)}: ${fieldS(level.level)} \n"
        }
        return value1
    }

    fun maxResolution(codecInfo: MediaCodecInfo): String {
        var maxReso = ""
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        val codecCapabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        for (codecInfo in codecList.codecInfos) {
            maxReso = codecCapabilities.videoCapabilities.supportedWidths.upper.toString()
                .let { maxWidth ->
                    codecCapabilities.videoCapabilities.supportedHeights.upper.toString()
                        .let { maxHeight ->
                            "$maxWidth x $maxHeight"
                        }
                }
        }
        return maxReso
    }

    val displayMetrics = context.resources.displayMetrics
    fun frameRatePerResolution(codecInfo: MediaCodecInfo): String {

        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        val codecCapabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        var maxFrameRate = ""

        for (codecInfo in codecList.codecInfos) {
            if (codecCapabilities.videoCapabilities.isSizeSupported(width, height)) {
                var maxFrameRateLow =
                    (codecCapabilities.videoCapabilities.getSupportedFrameRatesFor(
                        width,
                        height
                    ).lower).toInt()
                var maxFrameRateHigh =
                    (codecCapabilities.videoCapabilities.getSupportedFrameRatesFor(
                        width,
                        height
                    ).upper).toInt()

                maxFrameRate = "$maxFrameRateLow-$maxFrameRateHigh fps"
            }
        }
        return maxFrameRate

    }

    fun maxFrameRatePerResolution(codecInfo: MediaCodecInfo): String {
        val codecCapabilities = codecInfo.getCapabilitiesForType(codecInfo.supportedTypes[0])
        var maxFrameRate = ""
        var result=""
        // Define the resolutions
        val resolutions = listOf(
            Pair(144, 256),
            Pair(240, 426),
            Pair(360, 640),
            Pair(480, 854),
            Pair(720, 1280),
            Pair(1080, 1920),
            Pair(1440, 2560),
            Pair(2160, 3840),
            Pair(4320, 7680)
        )

        // resolutions list will now contain a list of pairs representing the supported resolutions
        // Get the max frame rate for each resolution
        for (resolution in resolutions) {
            if (codecCapabilities.videoCapabilities.isSizeSupported(resolution.first, resolution.second)) {
                val maxFrameRate = codecCapabilities.videoCapabilities.getSupportedFrameRatesFor(resolution.first, resolution.second).upper
                array1.add ("${resolution.first}p: ${maxFrameRate.toInt()} fps\n")

            }
        }
        for(i in array1){
            result+=i
        }
        return result
    }

    //extra
    fun conversion(int: Int) : String{
        return when {
            int < 1000  -> ("$int bps")
            int < 1000000 -> ("${int / 1000} kbps")
            int < 1000000000 -> ("${int / 1000000} Mbps")
            else -> ("${int / 1000000000} Gbps")
        }
    }

    fun fieldS(int: Int): String{
        var value = ""
        for (field in CodecProfileLevel::class.java.declaredFields) {
            if (field.type.toString() == "int") {
                val name = field.get(CodecProfileLevel()) as Int
                if( name == int){
                    value = field.name
                    break
                }
            }
        }
        return value
    }
}

