package com.diegopizzo.whosplaying.ui.detailsscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.common.LoadingView
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagerApi
class DetailsScreenActivity : ComponentActivity() {

    private val viewModel: DetailsScreenViewModel by viewModel()
    private var fixtureId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fixtureId = intent.getLongExtra(FIXTURE_ID_KEY, 0L)

        setContent {
            WhosPlayingTheme {
                val state = viewModel.viewStates().observeAsState()
                state.value?.let { AddContentView(it.eventDataModel, it.isLoading) }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFixtureEventDetails(fixtureId)
    }

    @Composable
    private fun AddContentView(dataModel: EventDataModel, isLoading: Boolean) {
        if (isLoading) {
            LoadingView()
        } else {
            MyScaffold(
                content = { ComposeDetailsView(dataModel) },
                navigationOnClick = { onBackPressedDispatcher.onBackPressed() })
        }
    }

    companion object {
        const val FIXTURE_ID_KEY = "FIXTURE_ID_KEY"
    }

    @Preview
    @Composable
    private fun Preview() {
        AddContentView(dataModel = detailsScreenPreviewDataModel, isLoading = false)
    }
}