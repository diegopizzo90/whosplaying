package com.diegopizzo.whosplaying.ui.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegopizzo.whosplaying.databinding.FragmentFixtureBinding
import com.diegopizzo.whosplaying.ui.base.FragmentViewBinding
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenActivity
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenActivity.Companion.FIXTURE_ID_KEY
import com.diegopizzo.whosplaying.ui.mainscreen.adapter.FixtureAdapter
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalPagerApi
class FixtureFragment : FragmentViewBinding<FragmentFixtureBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFixtureBinding
        get() = FragmentFixtureBinding::inflate

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
    }

    private val viewStateObserver = Observer<MainViewState> { viewState ->
        val adapter = binding.rvFixtures.adapter as FixtureAdapter
        adapter.addFixtures(viewState.fixtures)

        viewState.apply {
            if (updateFixture) {
                dateSelected?.let { date ->
                    viewModel.getFixturesByLeagueName(leagueCountrySelected, date)
                }
            }
        }
    }

    private fun toFixtureDetails(id: Long) {
        startActivity(Intent(requireActivity(), DetailsScreenActivity::class.java).apply {
            putExtra(FIXTURE_ID_KEY, id)
        })
    }

    private val viewEffectObserver = Observer<ViewEffect> {
        when (it) {
            ViewEffect.ShowErrorResult -> onError()
            ViewEffect.ShowSuccessResult -> onSuccess()
            ViewEffect.ShowProgressBar -> startShimmer()
        }
    }

    private fun setRecyclerView() {
        binding.rvFixtures.apply {
            adapter = FixtureAdapter { toFixtureDetails(it) }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun startShimmer() {
        binding.noEventsView.root.visibility = View.GONE
        binding.shimmerLoading.shimmerLayout.apply {
            visibility = View.VISIBLE
            startShimmer()
        }
    }

    private fun stopShimmer() {
        binding.shimmerLoading.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.noEventsView.root.visibility = View.GONE
        stopShimmer()
    }

    private fun onError() {
        binding.noEventsView.root.visibility = View.VISIBLE
        stopShimmer()
    }

    override fun onStop() {
        super.onStop()
        viewModel.viewStates().removeObserver(viewStateObserver)
        viewModel.viewEffects().removeObserver(viewEffectObserver)
        viewModel.onStopView()
    }
}