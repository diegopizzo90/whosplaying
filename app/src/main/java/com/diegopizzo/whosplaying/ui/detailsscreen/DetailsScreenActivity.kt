package com.diegopizzo.whosplaying.ui.detailsscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagerApi
class DetailsScreenActivity : ComponentActivity() {

    private val viewModel: DetailsScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fixtureId = intent.getLongExtra(FIXTURE_ID_KEY, 0L)
        viewModel.getFixtureEventDetails(fixtureId)

        setContent {
            WhosPlayingTheme {
                val state = viewModel.viewStates().observeAsState()
                state.value?.let { AddContentView(it.eventDataModel, it.isLoading) }
            }
        }
    }

    @Composable
    private fun AddContentView(dataModel: EventDataModel, isLoading: Boolean) {
        val modifier = if (isLoading) Modifier.shimmer() else Modifier
        MyScaffold(modifier = modifier,
            content = {
                ComposeDetailsView(dataModel)
            }, navigationOnClick = { onBackPressed() })
    }

    companion object {
        const val FIXTURE_ID_KEY = "FIXTURE_ID_KEY"
    }

    @Preview
    @Composable
    private fun Preview() {
        AddContentView(dataModel = dataModel, isLoading = false)
    }
}