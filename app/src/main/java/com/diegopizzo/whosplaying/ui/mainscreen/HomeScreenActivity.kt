package com.diegopizzo.whosplaying.ui.mainscreen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.view.HomeScreen
import com.diegopizzo.whosplaying.ui.standings.StandingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val standingsViewModel: StandingsViewModel by viewModel()
    private val detailsScreenViewModel: DetailsScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhosPlayingTheme {
                HomeScreen(
                    homeViewModel = homeViewModel,
                    standingsViewModel = standingsViewModel,
                    detailsScreenViewModel = detailsScreenViewModel,
                )
            }
        }
    }
}