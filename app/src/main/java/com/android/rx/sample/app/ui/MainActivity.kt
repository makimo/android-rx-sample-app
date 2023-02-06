package com.android.rx.sample.app.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.rx.sample.app.R
import com.android.rx.sample.app.utils.connect
import com.android.rx.sample.app.utils.format
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connect(this, mainViewModel.counter, ::onSimpleCount)
    }

    private fun onSimpleCount(count: Long) {
        val counter = findViewById<TextView>(R.id.txt_counter)
        counter.format(R.string.counter, count)
    }
}