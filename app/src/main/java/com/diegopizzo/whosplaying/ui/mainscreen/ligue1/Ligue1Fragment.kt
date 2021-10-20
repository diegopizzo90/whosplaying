package com.diegopizzo.whosplaying.ui.mainscreen.ligue1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.databinding.FragmentLigue1Binding
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewState
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseAdapter
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseFragmentLeague
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class Ligue1Fragment : BaseFragmentLeague<FragmentLigue1Binding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLigue1Binding
        get() = FragmentLigue1Binding::inflate

    override var shimmerLayout: ShimmerFrameLayout? = null
    override var noEventsView: AppCompatTextView? = null

    override val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        shimmerLayout = binding.shimmerLoading.shimmerLayout
        noEventsView = binding.noEventsView.root

        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFixturesByLeagueName(LeagueName.LIGUE_1)
        }
    }

    private val viewStateObserver = Observer<MainViewState> {
        val adapterLigue1 = binding.rvLigue1.adapter as AdapterLigue1
        adapterLigue1.addFixtures(it.fixtures)
    }

    private val viewEffectObserver = Observer<ViewEffect> {
        when (it) {
            ViewEffect.ShowErrorResult -> onError()
            ViewEffect.ShowSuccessResult -> onSuccess()
            ViewEffect.ShowProgressBar -> startShimmer()
        }
    }

    private fun setRecyclerView() {
        binding.rvLigue1.apply {
            adapter = AdapterLigue1()
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

internal class AdapterLigue1 : BaseAdapter()