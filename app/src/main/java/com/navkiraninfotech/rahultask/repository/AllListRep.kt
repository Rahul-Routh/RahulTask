package com.navkiraninfotech.rahultask.repository

import com.navkiraninfotech.rahultask.dataClass.ALlList
import com.surya.suryamfinderproject.extra.RetrofitInstance
import retrofit2.Response

object AllListRep {
    suspend fun callListDetails(params: Map<String, String>) : Response<ALlList> {
        return RetrofitInstance.getApi().getAllList(params)
    }
}