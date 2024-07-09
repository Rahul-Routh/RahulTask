package com.navkiraninfotech.rahultask.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.navkiraninfotech.rahultask.dataClass.ALlList
import com.navkiraninfotech.rahultask.repository.AllListRep.callListDetails
import kotlinx.coroutines.launch
import java.io.IOException


class AllListMasterModel(application: Application) : AndroidViewModel(application) {

    var allListData: MutableLiveData<ALlList> = MutableLiveData()
    val allListDataDataResponse: LiveData<ALlList> get() = allListData


    var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getAllMatList(kidId: String) {
        _isLoading.value = true
        val params: HashMap<String,String> = HashMap()
        params.put("kid_id", kidId)

        viewModelScope.launch {
            try{
                val response= callListDetails(params)

                if (response.code().toString() == "200") {
                    allListData.value = response.body()
                } else {
                    allListData.value = null
                }
                _isLoading.value = false

            }catch(exception: IOException) {
                allListData.value = null
                _isLoading.value = false
                exception.printStackTrace()
            }
        }

    }

}