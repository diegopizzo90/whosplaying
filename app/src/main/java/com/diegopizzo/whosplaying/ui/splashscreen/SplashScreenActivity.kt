package com.diegopizzo.whosplaying.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.diegopizzo.whosplaying.ui.mainscreen.MainScreenActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : ComponentActivity() {

    private val viewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.retrieveLeaguesInfo()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }, 3000)

        setContent {
            SplashScreenView()
        }
    }
}