package com.navkiraninfotech.rahultask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.navkiraninfotech.rahultask.R
import com.navkiraninfotech.rahultask.activity.MainActivity
import com.navkiraninfotech.rahultask.dataClass.AllListDetails
import com.navkiraninfotech.rahultask.databinding.ListViewDesignBinding

class AllListAdapter(val activity : MainActivity?) :
    RecyclerView.Adapter<AllListAdapter.ViewHolder>() {

    private val allListDataArrayList : ArrayList<AllListDetails>  = ArrayList()
    lateinit var binding : ListViewDesignBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_view_design, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = allListDataArrayList[position]
        holder.setIsRecyclable(false)

        binding.switchAssistant.setText(data.app_name)

        /*Glide.with(binding.imageIcon.context)
                .load(it.app_icon)
                .into(binding.imageIcon)*/

    }

    class ViewHolder (val binding: ListViewDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = allListDataArrayList.size

    fun updateList(list: List<AllListDetails>){
        allListDataArrayList.clear()
        allListDataArrayList.addAll(list)
        notifyDataSetChanged()
    }
}