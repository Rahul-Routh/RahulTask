package com.surya.suryamfinderproject.extra

import com.navkiraninfotech.rahultask.dataClass.ALlList
import com.surya.suryamfinderproject.extra.ApiList.List_URL
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    @POST(List_URL)
    @FormUrlEncoded
    suspend fun getAllList(@FieldMap params: Map<String, String>) : Response<ALlList>

}