package com.diegopizzo.whosplaying.ui.mainscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.databinding.ItemFixtureBinding

class FixtureAdapter(private val itemClickListener: (id: Long) -> Unit) :
    RecyclerView.Adapter<FixtureAdapter.BaseViewHolder>() {
    private val fixtureList: MutableList<FixtureDataModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        val binding = ItemFixtureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val fixture = fixtureList[position]
        holder.setFixture(fixture)
    }

    override fun getItemCount(): Int {
        return fixtureList.size
    }

    fun addFixtures(list: List<FixtureDataModel>) {
        fixtureList.clear()
        fixtureList.addAll(list)
        notifyDataSetChanged()
    }

    inner class BaseViewHolder(private val binding: ItemFixtureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setFixture(fixtureDataModel: FixtureDataModel) {
            binding.itemFixtureId.setFixture(fixtureDataModel)
            binding.itemFixtureId.setOnClickListener {
                itemClickListener(fixtureDataModel.fixtureId)
            }
        }
    }
}