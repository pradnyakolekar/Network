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
    var vendor:String
)
