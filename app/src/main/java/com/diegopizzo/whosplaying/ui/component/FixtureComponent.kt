package com.diegopizzo.whosplaying.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.model.StatusValue
import com.diegopizzo.whosplaying.databinding.ComponentFixtureBinding

class FixtureComponent(context: Context, attributeSet: AttributeSet) :
    CardView(context, attributeSet) {

    private val binding = ComponentFixtureBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFixture(fixture: FixtureDataModel) {
        binding.apply {
            fixture.apply {
                tvStatus.text = getFixtureStatus(status, elapsed, timeEvent)
                tvTeamHome.text = homeTeam
                setImageView(ivLogoHome, logoHomeTeam)
                tvTeamAway.text = awayTeam
                setImageView(ivLogoAway, logoAwayTeam)
                tvGoalHome.text = goalHomeTeam
                tvGoalAway.text = goalAwayTeam
                tvDateEvent.text = dateEvent
            }
        }
    }

    private fun setImageView(imageView: ImageView, logo: String) {
        Glide.with(this).load(logo).into(imageView)
    }

    fun hideDateEvent() {
        binding.apply {
            tvDateEvent.visibility = GONE
            dividerHorizontal.visibility = GONE
        }
    }

    fun showDateEvent() {
        binding.apply {
            tvDateEvent.visibility = VISIBLE
            dividerHorizontal.visibility = VISIBLE
        }
    }

    private fun getFixtureStatus(statusShort: String, elapsed: String?, timeEvent: String): String {
        if (elapsed == null) return timeEvent
        return when (statusShort) {
            StatusValue.FIRST_HALF.short, StatusValue.SECOND_HALF.short, StatusValue.EXTRA_TIME.short -> "$elapsed′"
            else -> statusShort
        }
    }
}