package com.diegopizzo.whosplaying.ui.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.databinding.ActivityMainBinding
import com.diegopizzo.whosplaying.ui.base.ActivityViewBinding
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSlider
import com.diegopizzo.whosplaying.ui.standings.StandingsActivity
import com.diegopizzo.whosplaying.ui.standings.StandingsActivity.Companion.STANDINGS_LEAGUE_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityViewBinding<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.serieA -> mainViewModel.onMenuNavigationSelected(LeagueName.SERIE_A)
                R.id.premierLeague -> mainViewModel.onMenuNavigationSelected(LeagueName.PREMIER_LEAGUE)
                R.id.laLiga -> mainViewModel.onMenuNavigationSelected(LeagueName.LA_LIGA)
                R.id.bundesliga -> mainViewModel.onMenuNavigationSelected(LeagueName.BUNDESLIGA)
                R.id.ligue1 -> mainViewModel.onMenuNavigationSelected(LeagueName.LIGUE_1)
            }
            true
        }

        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_menu)
            setOnMenuItemClickListener {
                startActivity(Intent(this@MainScreenActivity, StandingsActivity::class.java).apply {
                    putExtra(STANDINGS_LEAGUE_KEY, mainViewModel.viewState.leagueSelected.name)
                })
                true
            }
        }
        setDatePickerSlider()
    }

    private fun setDatePickerSlider() {
        binding.datePickerSlider.setContent {
            WhosPlayingTheme {
                mainViewModel.apply {
                    DatePickerSlider(
                        viewState.datePickerSliderModel,
                        viewState.indexDateSelected,
                        { onDaySelected(it) }
                    )
                }
            }
        }
    }
}