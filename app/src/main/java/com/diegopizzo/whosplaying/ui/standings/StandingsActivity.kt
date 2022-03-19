package com.diegopizzo.whosplaying.ui.standings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.common.LoadingView
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsActivity : ComponentActivity() {

    private val viewModel: StandingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(STANDINGS_LEAGUE_KEY)
            ?.let { viewModel.getStandings(LeagueName.valueOf(it)) }

        setContent {
            val state = viewModel.viewStates().observeAsState()
            WhosPlayingTheme {
                AddContentView(
                    state.value?.standings ?: emptyList(),
                    state.value?.isLoading ?: false
                )
            }
        }
    }

    @Composable
    private fun AddContentView(standings: List<StandingsDataModel>, isLoading: Boolean) {
        if (isLoading) {
            LoadingView()
        } else {
            MyScaffold(content = { Standings(standings) }, navigationOnClick = { onBackPressed() })
        }
    }

    companion object {
        const val STANDINGS_LEAGUE_KEY = "STANDINGS_LEAGUE_KEY"
    }

    @Preview
    @Composable
    private fun Preview() {
        AddContentView(standingsMock, false)
    }
}