package com.omagrahari.niyotailassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omagrahari.niyotailassignment.R
import com.omagrahari.niyotailassignment.databinding.LayoutNearbyspotItemBinding
import com.omagrahari.niyotailassignment.entity.Result

class NearbySpotsAdapter(private var results: List<Result>) :
    RecyclerView.Adapter<NearbySpotsAdapter.ViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutNearbyspotItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_nearbyspot_item, parent, false
        )
        return ViewModel(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        holder.binding!!.nearbySpot = results.get(position)

        if (!results.get(position).photos.isNullOrEmpty())
            holder.binding!!.photo = results.get(position).photos!!.get(0)

        holder.binding!!.executePendingBindings()
    }

    inner class ViewModel(binding: LayoutNearbyspotItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        var binding: LayoutNearbyspotItemBinding? = null

        init {
            this.binding = binding

            this.binding!!.cvRoot.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var result: Result = results.get(adapterPosition)
            when (p0!!.id) {
                R.id.cvRoot -> {
                    if (result.isSelected == false)
                        result.isSelected = true
                    else
                        result.isSelected = false

                    notifyDataSetChanged()
                }
            }
        }


    }
}