package com.diegopizzo.whosplaying.ui.mainscreen.base

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewbinding.ViewBinding
import com.diegopizzo.whosplaying.ui.base.FragmentViewBinding
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenActivity
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.facebook.shimmer.ShimmerFrameLayout

abstract class BaseFragmentLeague<VB : ViewBinding> : FragmentViewBinding<VB>() {

    abstract var shimmerLayout: ShimmerFrameLayout?
    abstract var noEventsView: AppCompatTextView?
    internal abstract val viewModel: MainViewModel

    protected fun startShimmer() {
        shimmerLayout?.visibility = View.VISIBLE
        shimmerLayout?.startShimmer()
    }

    private fun stopShimmer() {
        shimmerLayout?.stopShimmer()
        shimmerLayout?.visibility = View.GONE
    }

    protected fun onSuccess() {
        noEventsView?.visibility = View.GONE
        stopShimmer()
    }

    protected fun onError() {
        noEventsView?.visibility = View.VISIBLE
        stopShimmer()
    }

    protected fun toFixtureDetails(id: Long) {
        startActivity(Intent(activity, DetailsScreenActivity::class.java).apply {
            putExtra(FIXTURE_ID_KEY, id)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shimmerLayout = null
        noEventsView = null
    }

    companion object {
        const val FIXTURE_ID_KEY = "FIXTURE_ID_KEY"
    }
}