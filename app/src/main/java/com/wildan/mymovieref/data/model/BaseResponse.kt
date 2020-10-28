package com.wildan.mymovieref.data.model


import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0

    @SerializedName("total_results")
    var totalResults: Int = 0
}

