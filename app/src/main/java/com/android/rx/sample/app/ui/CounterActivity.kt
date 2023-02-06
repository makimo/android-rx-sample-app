package com.android.rx.sample.app.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.rx.sample.app.R
import com.android.rx.sample.app.ui.CounterViewModel.Companion.RANGE_END
import com.android.rx.sample.app.ui.CounterViewModel.Companion.RANGE_START
import com.android.rx.sample.app.utils.click
import com.android.rx.sample.app.utils.connect
import com.android.rx.sample.app.utils.format
import com.android.rx.sample.app.utils.toggleClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class CounterActivity : AppCompatActivity() {

    private val counterViewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt_range.format(R.string.range, RANGE_START, RANGE_END)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        btn_add.click {
            counterViewModel.add()
        }

        btn_subtract.click {
            counterViewModel.subtract()
        }

        btn_toggle_adding.toggleClick { _, b ->
            counterViewModel.toggleAdding(b)
        }

        btn_toggle_subtraction.toggleClick { _, b ->
            counterViewModel.toggleSubtraction(b)
        }
    }

    private fun setupObservers() {
        connect(this, counterViewModel.counter, ::onSimpleCount)
        connect(this, counterViewModel.lastUpdate, ::onLastUpdate)
        connect(this, counterViewModel.onEnabledAdding, ::onEnableAdding)
        connect(this, counterViewModel.onEnabledSubtraction, ::onEnabledSubtraction)
        connect(this, counterViewModel.onDisableAdding, ::onDisableAdding)
        connect(this, counterViewModel.onDisableSubtraction, ::onDisableSubtraction)
        connect(this, counterViewModel.onDataChange, ::onDataChange)
    }

    private fun onSimpleCount(count: Int) {
        txt_counter.format(R.string.counter, count)
    }

    private fun onLastUpdate(date: String) {
        txt_last_update.format(R.string.last_update, date)
    }

    private fun onEnableAdding() {
        btn_add.isEnabled = true
    }

    private fun onEnabledSubtraction() {
        btn_subtract.isEnabled = true
    }

    private fun onDisableAdding() {
        btn_add.isEnabled = false
    }

    private fun onDisableSubtraction() {
        btn_subtract.isEnabled = false
    }

    private fun onDataChange(data: List<Any>) {
        val (counter, lastUpdate, addingEnabled, subtractionEnabled) = data
        println(
            "${javaClass.simpleName} onDataChange: " +
                    "counter $counter, " +
                    "lastUpdate: $lastUpdate, " +
                    "addingEnabled: $addingEnabled, " +
                    "subtractionEnabled: $subtractionEnabled"
        )
    }
}