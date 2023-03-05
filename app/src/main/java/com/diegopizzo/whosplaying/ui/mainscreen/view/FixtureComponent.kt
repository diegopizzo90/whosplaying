package com.diegopizzo.whosplaying.ui.mainscreen.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import com.bumptech.glide.Glide
import com.diegopizzo.network.CommonConstant.TIME_PATTERN
import com.diegopizzo.network.Util
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.model.StatusValue
import com.diegopizzo.whosplaying.databinding.ComponentFixtureBinding
import com.diegopizzo.whosplaying.ui.blinkingcircle.BlinkingCircleView
import org.threeten.bp.ZoneId

class FixtureComponent(context: Context, attributeSet: AttributeSet) :
    CardView(context, attributeSet) {

    private val binding = ComponentFixtureBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFixture(fixture: FixtureDataModel) {
        binding.apply {
            fixture.apply {
                val timeEvent = Util.convertUtcDateTimeToLocal(
                    dateTimeEventUtc,
                    ZoneId.systemDefault(),
                    TIME_PATTERN
                )
                tvStatus.text = getFixtureStatus(status, elapsed, timeEvent)
                tvTeamHome.text = homeTeam
                setImageView(ivLogoHome, logoHomeTeam)
                tvTeamAway.text = awayTeam
                setImageView(ivLogoAway, logoAwayTeam)
                tvGoalHome.text = goalHomeTeam
                tvGoalAway.text = goalAwayTeam

                blinkingCircle.apply {
                    // Dispose of the Composition when the view's LifecycleOwner is destroyed
                    setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        if (isFixtureLive(status)) BlinkingCircleView()
                    }
                }
            }
        }
    }

    private fun setImageView(imageView: ImageView, logo: String) {
        Glide.with(this).load(logo).into(imageView)
    }

    private fun getFixtureStatus(statusShort: String, elapsed: String?, timeEvent: String): String {
        return when (statusShort) {
            StatusValue.FIRST_HALF.short, StatusValue.SECOND_HALF.short, StatusValue.EXTRA_TIME.short -> "$elapsed′"
            StatusValue.NOT_STARTED.short -> timeEvent
            else -> statusShort
        }
    }

    private fun isFixtureLive(statusShort: String): Boolean {
        return when (statusShort) {
            StatusValue.FIRST_HALF.short, StatusValue.SECOND_HALF.short, StatusValue.EXTRA_TIME.short -> true
            else -> false
        }
    }
}