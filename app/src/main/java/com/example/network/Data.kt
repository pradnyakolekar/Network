package com.example.network

class DataModel(var title: String, var desc: String) {

    fun gettitle(): String {
        return title
    }

    fun getdesc(): String {
        return desc
    }
}

data class DataModel1 (
    var title:String,
    var desc: String,
    var hwAcc:String,
    var swonly:String,
    var vendor:String,
    var lowlatency:String,
    var maxInstance:String,
    var bitrange:String,
    var range:String,
    var range2:String,
    var feature:String,
    var multiaccess:String,
    var partailframe:String,
    var tunneledframe:String,
    var bitrateModes:String
)

data class DataModel2 (
    var title:String,
    var desc: String,
    var hwAcc:String,
    var swonly:String,
    var vendor:String,
    var lowlatency:String,
    var maxInstance:String,
    var bitrange:String,
    var range:String,
    var range2:String,
    var feature:String,
    var multiaccess:String,
    var partailframe:String,
    var tunneledframe:String
)
