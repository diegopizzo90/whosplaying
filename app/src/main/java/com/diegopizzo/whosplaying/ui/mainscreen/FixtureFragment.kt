package com.diegopizzo.whosplaying.ui.mainscreen.fixture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.databinding.FragmentFixtureBinding
import com.diegopizzo.whosplaying.ui.base.FragmentViewBinding
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenActivity
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewState
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.diegopizzo.whosplaying.ui.mainscreen.adapter.FixtureAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate

class FixtureFragment : FragmentViewBinding<FragmentFixtureBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFixtureBinding
        get() = FragmentFixtureBinding::inflate

    private val viewModel: MainViewModel by sharedViewModel()
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
        getFixtures(viewModel.viewState.dateSelected)
    }

    private fun getFixtures(date: LocalDate?) {
        job?.cancel()
        job = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFixturesByLeagueName(LeagueName.SERIE_A, date)
        }
    }

    private val viewStateObserver = Observer<MainViewState> { viewState ->
        val adapter = binding.rvFixtures.adapter as FixtureAdapter
        adapter.addFixtures(viewState.fixtures)

        if (viewState.isNewDaySelected) viewState.dateSelected?.let { date -> getFixtures(date) }
    }

    private val viewEffectObserver = Observer<ViewEffect> {
        when (it) {
            ViewEffect.ShowErrorResult -> onError()
            ViewEffect.ShowSuccessResult -> onSuccess()
            ViewEffect.ShowProgressBar -> startShimmer()
            is ViewEffect.ShowFixtureDetails -> toFixtureDetails(it.id)
        }
    }

    private fun setRecyclerView() {
        binding.rvFixtures.apply {
            adapter = FixtureAdapter { viewModel.onFixtureSelected(it) }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun startShimmer() {
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

    private fun toFixtureDetails(id: Long) {
        startActivity(Intent(activity, DetailsScreenActivity::class.java).apply {
            putExtra(FIXTURE_ID_KEY, id)
        })
    }

    companion object {
        const val FIXTURE_ID_KEY = "FIXTURE_ID_KEY"
    }
}