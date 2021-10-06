package com.diegopizzo.whosplaying.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.retrieveLeaguesInfo()
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 3000)
    }
}