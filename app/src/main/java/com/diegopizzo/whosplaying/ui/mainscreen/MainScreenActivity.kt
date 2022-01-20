package com.diegopizzo.whosplaying.ui.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.databinding.ActivityMainBinding
import com.diegopizzo.whosplaying.ui.base.ActivityViewBinding
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSlider
import com.diegopizzo.whosplaying.ui.standings.StandingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityViewBinding<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val viewModel: StandingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_nav_controller) as NavHostFragment).navController
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.serieAFragment -> viewModel.onMenuItemSelected(LeagueName.SERIE_A)
                    R.id.premierLeagueFragment -> viewModel.onMenuItemSelected(LeagueName.PREMIER_LEAGUE)
                    R.id.laLigaFragment -> viewModel.onMenuItemSelected(LeagueName.LA_LIGA)
                    R.id.bundesligaFragment -> viewModel.onMenuItemSelected(LeagueName.BUNDESLIGA)
                    R.id.ligue1Fragment -> viewModel.onMenuItemSelected(LeagueName.LIGUE_1)
                }
            }
        }
        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_menu)
            setOnMenuItemClickListener {
                navController.navigate(R.id.standingsFragment)
                true
            }
        }
        setDatePickerSlider()
    }

    private fun setDatePickerSlider() {
        binding.datePickerSlider.setContent {
            WhosPlayingTheme {
                DatePickerSlider()
            }
        }
    }
}