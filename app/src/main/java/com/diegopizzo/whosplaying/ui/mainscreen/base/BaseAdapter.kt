package com.diegopizzo.whosplaying.ui.mainscreen.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diegopizzo.network.Util
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.databinding.ItemFixtureBinding

abstract class BaseAdapter : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {
    protected val fixtureList: MutableList<FixtureDataModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val binding = ItemFixtureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val fixture = fixtureList[position]
        holder.setFixture(fixture, position)
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
        fun setFixture(fixtureDataModel: FixtureDataModel, position: Int) {
            binding.itemFixtureId.setFixture(fixtureDataModel)
            displayFixturesDateTime(position)
        }

        private fun displayFixturesDateTime(position: Int) {
            if (position > 0) {
                val previousFixture = fixtureList[position - 1]
                val newFixture = fixtureList[position]

                if (Util.areDatesWithoutTimesEquals(
                        previousFixture.dateTimeEventUtc, newFixture.dateTimeEventUtc
                    )
                ) {
                    binding.itemFixtureId.hideDateEvent()
                } else binding.itemFixtureId.showDateEvent()

            } else binding.itemFixtureId.showDateEvent()
        }
    }
}