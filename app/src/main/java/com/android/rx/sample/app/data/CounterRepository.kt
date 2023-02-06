package com.android.rx.sample.app.data

import com.android.rx.sample.app.utils.defaultBehavior

class CounterRepository {

    val counter = defaultBehavior(0)

    val lastUpdate = counter.timestamp().map { it.time() }

    val addingEnabled = defaultBehavior(true)

    val subtractionEnabled = defaultBehavior(true)

    fun add() = counter.onNext(counter.value?.inc())

    fun subtract() = counter.onNext(counter.value?.dec())

    fun toggleAdding(enabled: Boolean) = addingEnabled.onNext(enabled)

    fun toggleSubtraction(enabled: Boolean) = subtractionEnabled.onNext(enabled)
}