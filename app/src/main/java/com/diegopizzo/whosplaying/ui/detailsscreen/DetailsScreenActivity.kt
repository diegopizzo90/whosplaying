package com.diegopizzo.whosplaying.ui.detailsscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseFragmentLeague.Companion.FIXTURE_ID_KEY
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsScreenActivity : ComponentActivity() {

    private val viewModel: DetailsScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fixtureId = intent.getLongExtra(FIXTURE_ID_KEY, 0L)

        lifecycleScope.launch {
            viewModel.getFixtureEventDetails(fixtureId)
        }

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
            title = stringResource(R.string.app_name),
            content = {
                ComposeDetailsView(dataModel)
            }, navigationOnClick = { onBackPressed() })
    }

    @Preview
    @Composable
    private fun Preview() {
        AddContentView(dataModel = dataModel, isLoading = false)
    }
}