package com.navkiraninfotech.rahultask.activity

import android.app.Application
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.navkiraninfotech.rahultask.R
import com.navkiraninfotech.rahultask.adapter.AllListAdapter
import com.navkiraninfotech.rahultask.dataClass.AllListDetails
import com.navkiraninfotech.rahultask.databinding.ActivityMainBinding
import com.navkiraninfotech.rahultask.global.CustomProgressDialog
import com.navkiraninfotech.rahultask.model.AllListMasterModel
import java.util.Objects

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val allListAdapter by lazy {  AllListAdapter(this) }
    private val allListMasterModel by lazy {  AllListMasterModel(Application()) }
    private val allListDetailsArrayList : ArrayList<AllListDetails>  = ArrayList()

    lateinit var imageInString:String
    var uriGet: Uri? = null

    val customeprogressdialog by lazy { this.let { CustomProgressDialog(it) } }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        allListMasterModel.getAllMatList("378")

        binding.swipeRefresh.setOnRefreshListener {
            allListMasterModel.getAllMatList("378")
            allListDetailsArrayList.clear()
        }

        allListMasterModel.allListDataDataResponse.observe(this) {
            if (it == null){
                Toast.makeText(this, "Server Not Response Properly", Toast.LENGTH_SHORT).show()
            }else {
                if (it.success.equals(true)) {
                    allListDetailsArrayList.clear()

                    Log.d("Datsadsadsdsadsadsaa", it.data.app_list.toString())

                    if (binding.swipeRefresh.isRefreshing)
                        binding.swipeRefresh.isRefreshing = false


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

        binding.uploadPhoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        binding.imageProfile.setOnClickListener {
            val builder = Dialog(this)
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
            Objects.requireNonNull(builder.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val imageView = ImageView(this)

            Glide.with(this).load(uriGet).placeholder(R.drawable.profile_pic).into(imageView)

            val layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            builder.addContentView(imageView, layoutParams)
            builder.show()
        }

        allListMasterModel.isLoading.observe(this){
            if(it){
                customeprogressdialog.start("Please Wait...")
            }else{
                customeprogressdialog.stop()
            }
        }

    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Glide.with(this).load(uri).into(binding.imageProfile)
            uriGet = uri
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

}