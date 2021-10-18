package com.diegopizzo.whosplaying.ui.mainscreen.seriea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.databinding.FragmentSerieABinding
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewState
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseAdapter
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseFragmentLeague
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SerieAFragment : BaseFragmentLeague<FragmentSerieABinding>() {

    private lateinit var adapterSerieA: AdapterSerieA
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSerieABinding
        get() = FragmentSerieABinding::inflate

    private val viewModel: MainViewModel by sharedViewModel()

    override val shimmerLayout: ShimmerFrameLayout
        get() = binding.shimmerLoading.shimmerLayout

    override val noEventsView: AppCompatTextView
        get() = binding.noEventsView.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFixturesByLeagueName(LeagueName.SERIE_A)
        }
    }

    private val viewStateObserver = Observer<MainViewState> {
        adapterSerieA.addFixtures(it.fixtures)
    }

    private val viewEffectObserver = Observer<ViewEffect> {
        when (it) {
            ViewEffect.ShowErrorResult -> onError()
            ViewEffect.ShowSuccessResult -> onSuccess()
            ViewEffect.ShowProgressBar -> startShimmer()
        }
    }

    private fun setRecyclerView() {
        adapterSerieA = AdapterSerieA()
        binding.rvSerieA.apply {
            adapter = adapterSerieA
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

internal class AdapterSerieA : BaseAdapter()