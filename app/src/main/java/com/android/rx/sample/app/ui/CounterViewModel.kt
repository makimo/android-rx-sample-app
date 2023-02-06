package com.android.rx.sample.app.ui

import androidx.lifecycle.ViewModel
import com.android.rx.sample.app.R
import com.android.rx.sample.app.data.CounterRepository
import com.android.rx.sample.app.utils.string
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val counterRepository: CounterRepository
) : ViewModel() {

    companion object {
        const val RANGE_START = 0
        const val RANGE_END = 20
    }

    /**
     * Stores the last counter value.
     */
    val counter = counterRepository.counter

    /**
     * Stores the last update date of the counter value.
     */
    val lastUpdate = counterRepository.lastUpdate.map {
        SimpleDateFormat(string(R.string.date_format)).format(Date(it))
    }

    /**
     * Called when adding is enabled (addingEnabled == true)
     * or counter is less than [RANGE_END].
     */
    val onEnabledAdding =
        Observables.combineLatest(
            counterRepository.addingEnabled,
            counterRepository.counter
        ).filter { (enabled, counter) ->
            enabled || counter < RANGE_END
        }.map { }

    /**
     * Called when subtraction is enabled (subtractionEnabled == true)
     * or counter is greater than [RANGE_START].
     */
    val onEnabledSubtraction =
        Observables.combineLatest(
            counterRepository.subtractionEnabled,
            counterRepository.counter
        ).filter { (enabled, counter) ->
            enabled || counter > RANGE_START
        }.map { }

    /**
     * Called when adding is disabled (addingEnabled == false)
     * or counter is equal to [RANGE_END].
     */
    val onDisableAdding =
        Observables.combineLatest(
            counterRepository.addingEnabled,
            counterRepository.counter
        ).filter { (enabled, counter) ->
            !enabled || counter == RANGE_END
        }.map { }

    /**
     * Called when subtraction is disabled (subtractionEnabled == false)
     * or counter is equal to [RANGE_START].
     */
    val onDisableSubtraction =
        Observables.combineLatest(
            counterRepository.subtractionEnabled,
            counterRepository.counter
        ).filter { (enabled, counter) ->
            !enabled || counter == RANGE_START
        }.map { }

    /**
     * Triggered when any of given streams will change.
     */
    val onDataChange =
        Observable.combineLatest(
            counterRepository.counter,
            lastUpdate,
            counterRepository.addingEnabled,
            counterRepository.subtractionEnabled
        ) { a, b, c, d -> listOf(a, b, c, d) }

    fun add() = counterRepository.add()

    fun subtract() = counterRepository.subtract()

    fun toggleAdding(enabled: Boolean) = counterRepository.toggleAdding(enabled)

    fun toggleSubtraction(enabled: Boolean) = counterRepository.toggleSubtraction(enabled)
}