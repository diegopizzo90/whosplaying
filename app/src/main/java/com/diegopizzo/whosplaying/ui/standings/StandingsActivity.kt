package com.diegopizzo.whosplaying.ui.standings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsActivity : ComponentActivity() {

    private val viewModel: StandingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.viewStates().observeAsState()
            AddContentView(
                state.value?.standings ?: emptyList(),
                state.value?.isLoading ?: false
            )
        }
        intent.getStringExtra(STANDINGS_LEAGUE_KEY)
            ?.let { viewModel.getStandings(LeagueName.valueOf(it)) }
    }

    @Composable
    private fun AddContentView(standings: List<StandingsDataModel>, isLoading: Boolean) {
        WhosPlayingTheme {
            MyScaffold(content = {
                Standings(standings, if (isLoading) Modifier.shimmer() else Modifier)
            }, navigationOnClick = { onBackPressed() })
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