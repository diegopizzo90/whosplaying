package com.diegopizzo.whosplaying.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme

class StandingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AddContentView(listOf())
            }
        }
    }

    @Composable
    private fun AddContentView(standings: List<StandingsDataModel>) {
        WhosPlayingTheme {
            Standings(standings)
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        AddContentView(standingsMock)
    }
}