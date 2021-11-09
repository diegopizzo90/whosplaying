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
import org.koin.androidx.viewmodel.ext.android.viewModel

class SerieAFragment : BaseFragmentLeague<FragmentSerieABinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSerieABinding
        get() = FragmentSerieABinding::inflate


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
            viewModel.getFixturesByLeagueName(LeagueName.SERIE_A)
        }
    }

    private val viewStateObserver = Observer<MainViewState> {
        val adapterSerieA = binding.rvSerieA.adapter as AdapterSerieA
        adapterSerieA.addFixtures(it.fixtures)
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
        binding.rvSerieA.apply {
            adapter = AdapterSerieA {
                viewModel.onFixtureSelected(it)
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

internal class AdapterSerieA(itemClickListener: (id: Long) -> Unit) : BaseAdapter(itemClickListener)