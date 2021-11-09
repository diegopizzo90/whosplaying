package com.diegopizzo.whosplaying.ui.mainscreen.laliga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.databinding.FragmentLaLigaBinding
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewState
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseAdapter
import com.diegopizzo.whosplaying.ui.mainscreen.base.BaseFragmentLeague
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaLigaFragment : BaseFragmentLeague<FragmentLaLigaBinding>() {

    override val viewModel: MainViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLaLigaBinding
        get() = FragmentLaLigaBinding::inflate

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
            viewModel.getFixturesByLeagueName(LeagueName.LA_LIGA)
        }
    }

    private val viewStateObserver = Observer<MainViewState> {
        val adapterLaLiga = binding.rvLaLiga.adapter as AdapterLaLiga
        adapterLaLiga.addFixtures(it.fixtures)
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
        binding.rvLaLiga.apply {
            adapter = AdapterLaLiga {
                viewModel.onFixtureSelected(it)
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

internal class AdapterLaLiga(itemClickListener: (id: Long) -> Unit) : BaseAdapter(itemClickListener)