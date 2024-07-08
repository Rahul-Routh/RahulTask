package com.navkiraninfotech.rahultask.dataClass

import com.google.gson.annotations.SerializedName

data class ALlList(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : Data,
)
data class Data(
    @SerializedName("app_list") var app_list : List<AllListDetails>
)
data class AllListDetails(
    @SerializedName("app_id") var app_id : String,
    @SerializedName("fk_kid_id") var fk_kid_id : String,
    @SerializedName("kid_profile_image") var kid_profile_image : String,
    @SerializedName("app_name") var app_name : String,
    @SerializedName("app_icon") var app_icon : String,
    @SerializedName("app_package_name") var app_package_name : String,
    @SerializedName("status") var status : String
    )