package com.gmail.vexonelite.startproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gmail.vexonelite.startproject.databinding.ActivityMainBinding
import replaceFragment

class MainActivity : AppCompatActivity() {

    // R.layout.activity_main
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        replaceFragment(EntryFragment(), R.id.fragmentContainer)
    }
}