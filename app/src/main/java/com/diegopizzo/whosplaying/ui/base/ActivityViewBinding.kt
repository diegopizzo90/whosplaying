package com.diegopizzo.whosplaying.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ActivityViewBinding<VB : ViewBinding> : AppCompatActivity() {

    //Provide view binding instance
    protected lateinit var binding: VB
    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}