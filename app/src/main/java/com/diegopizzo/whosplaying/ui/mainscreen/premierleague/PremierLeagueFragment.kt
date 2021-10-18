package com.diegopizzo.whosplaying.ui.mainscreen.premierleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.databinding.FragmentPremierLeagueBinding
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewState
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseAdapter
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseFragmentLeague
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PremierLeagueFragment : BaseFragmentLeague<FragmentPremierLeagueBinding>() {

    private lateinit var adapterPremierLeague: AdapterPremierLeague

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPremierLeagueBinding
        get() = FragmentPremierLeagueBinding::inflate

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
            viewModel.getFixturesByLeagueName(LeagueName.PREMIER_LEAGUE)
        }
    }

    private val viewStateObserver = Observer<MainViewState> {
        adapterPremierLeague.addFixtures(it.fixtures)
    }

    private val viewEffectObserver = Observer<ViewEffect> {
        when (it) {
            ViewEffect.ShowErrorResult -> binding.noEventsView.root.visibility = View.VISIBLE
            ViewEffect.ShowSuccessResult -> binding.noEventsView.root.visibility = View.GONE
        }
    }

    private fun setRecyclerView() {
        adapterPremierLeague = AdapterPremierLeague()
        binding.rvPremierLeague.apply {
            adapter = adapterPremierLeague
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

internal class AdapterPremierLeague : BaseAdapter()