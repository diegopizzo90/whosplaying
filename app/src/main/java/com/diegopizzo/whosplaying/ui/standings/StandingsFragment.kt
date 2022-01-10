package com.diegopizzo.whosplaying.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StandingsFragment : Fragment() {

    private val viewModel: StandingsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state = viewModel.viewStates().observeAsState()
                AddContentView(
                    state.value?.standings ?: emptyList(),
                    state.value?.isLoading ?: false
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getStandings()
        }
    }

    @Composable
    private fun AddContentView(standings: List<StandingsDataModel>, isLoading: Boolean) {
        WhosPlayingTheme {
            Standings(standings, if (isLoading) Modifier.shimmer() else Modifier)
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        AddContentView(standingsMock, false)
    }
}