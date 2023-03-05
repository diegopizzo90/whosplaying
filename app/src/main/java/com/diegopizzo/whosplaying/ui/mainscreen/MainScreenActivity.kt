package com.diegopizzo.whosplaying.ui.mainscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_menu)
            setOnMenuItemClickListener {
                startActivity(Intent(this@MainScreenActivity, StandingsActivity::class.java).apply {
                    putExtra(STANDINGS_LEAGUE_KEY, mainViewModel.viewState.leagueSelected.name)
                })
                true
            }
        }*/
    }
}