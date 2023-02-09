package com.sultanseidov.genericelectricalenergycostcalculator.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sultanseidov.genericelectricalenergycostcalculator.databinding.ActivityMainBinding
import com.sultanseidov.genericelectricalenergycostcalculator.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private lateinit var binding: ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}