package com.diegopizzo.whosplaying.ui.mainscreen.base

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewbinding.ViewBinding
import com.diegopizzo.whosplaying.ui.base.FragmentViewBinding
import com.facebook.shimmer.ShimmerFrameLayout

abstract class BaseFragmentLeague<VB : ViewBinding> : FragmentViewBinding<VB>() {

    abstract var shimmerLayout: ShimmerFrameLayout?
    abstract var noEventsView: AppCompatTextView?

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

    override fun onDestroyView() {
        super.onDestroyView()
        shimmerLayout = null
        noEventsView = null
    }
}