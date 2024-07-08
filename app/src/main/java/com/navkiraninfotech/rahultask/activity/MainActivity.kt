package com.navkiraninfotech.rahultask.activity

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
                    allListDetailsArrayList.clear()

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

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(query1: CharSequence, start: Int, before: Int, count: Int) {


                var query = query1
                query = query.toString().uppercase()
                val filteredList: ArrayList<AllListDetails> = ArrayList()
                for (i in 0 until allListDetailsArrayList.size) {
                    val appName: String = allListDetailsArrayList.get(i).app_name.uppercase()

                    if (appName.contains(query)) {
                        filteredList.add(allListDetailsArrayList.get(i))
                    }
                }
                allListAdapter.updateList(filteredList)
                binding.recyclerView.setAdapter(allListAdapter)


            }
        })

    }

}