package com.example.network

class DataModel(var title: String, var desc: String) {

    fun gettitle(): String {
        return title
    }

    fun getdesc(): String {
        return desc
    }
}

data class DataModel2 (
    var title:String,
    var desc: String,
    var hwAcc:String,
    var swonly:String,
    var vendor:String,
    var lowlatency:String,
    var maxInstance:String,
    var bitrange:String?,
    var channelcount: String?,
    var profile:String?,
    var color:String?,
    var dynamic:String,
    var multiaccess:String,
    var partialframe:String,
    var tunneledframe:String,
    var maxReso:String?,
    var frame:String?,
    var maxframe:String?,
    var bitratemodes:String?,
    var isHDR:String?,
    var adaptivePlayback:String?,
    var securePlayback:String?,
    var infraRefresh:String?,
)
