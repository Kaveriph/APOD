package com.kaveri.gs.apod.model.pojo

data class APOD(
    var date: String? = null,
    var explanation: String? = null,
    var hdurl: String? = null,
    var mediaType: String? = null,
    var serviceVersion: String? = null,
    var title: String? = null,
    var url: String? = null,
    var fav: Boolean = false)
