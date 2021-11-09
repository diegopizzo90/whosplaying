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
import org.koin.androidx.viewmodel.ext.android.viewModel

class PremierLeagueFragment : BaseFragmentLeague<FragmentPremierLeagueBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPremierLeagueBinding
        get() = FragmentPremierLeagueBinding::inflate

    override val viewModel: MainViewModel by viewModel()

    override var shimmerLayout: ShimmerFrameLayout? = null

    override var noEventsView: AppCompatTextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        shimmerLayout = binding.shimmerLoading.shimmerLayout
        noEventsView = binding.noEventsView.root

        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFixturesByLeagueName(LeagueName.PREMIER_LEAGUE)
        }
    }

    private val viewStateObserver = Observer<MainViewState> {
        val adapterPremierLeague = binding.rvPremierLeague.adapter as AdapterPremierLeague
        adapterPremierLeague.addFixtures(it.fixtures)
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
        binding.rvPremierLeague.apply {
            adapter = AdapterPremierLeague {
                viewModel.onFixtureSelected(it)
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

internal class AdapterPremierLeague(itemClickListener: (id: Long) -> Unit) : BaseAdapter(itemClickListener)