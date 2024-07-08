package com.navkiraninfotech.rahultask.activity

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.navkiraninfotech.rahultask.R
import com.navkiraninfotech.rahultask.adapter.AllListAdapter
import com.navkiraninfotech.rahultask.dataClass.AllListDetails
import com.navkiraninfotech.rahultask.databinding.ActivityMainBinding
import com.navkiraninfotech.rahultask.model.AllListMasterModel

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val allListAdapter by lazy {  AllListAdapter(this) }
    private val allListMasterModel by lazy {  AllListMasterModel(Application()) }
    private val allListDetailsArrayList : ArrayList<AllListDetails>  = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        allListMasterModel.getAllMatList("378")

        allListMasterModel.allListDataDataResponse.observe(this) {
            if (it == null){
                Toast.makeText(this, "Server Not Response Properly", Toast.LENGTH_SHORT).show()
            }else {
                if (it.success.equals(true)) {
                    Log.d("Datsadsadsdsadsadsaa", it.data.app_list.toString())

                    it.data.app_list.forEach {
                        allListDetailsArrayList.add(it)
                    }

                    binding.recyclerView.apply {
                        adapter = allListAdapter.also { adapter ->
                            adapter.updateList(allListDetailsArrayList)
                        }
                        val linerLayoutManager = LinearLayoutManager(this@MainActivity,
                            LinearLayoutManager.VERTICAL,false)
                        setHasFixedSize(true)
                        layoutManager = linerLayoutManager
                    }
                }
            }

        }

    }

}