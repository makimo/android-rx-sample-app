package com.android.rx.sample.app.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

fun <T> defaultBehavior(default: T) = BehaviorSubject.createDefault(default)

fun <T : Any> fromPublisher(observable: Observable<T>) =
    LiveDataReactiveStreams.fromPublisher(
        observable.toFlowable(BackpressureStrategy.LATEST)
    )

fun <T : Any> Observable<T>.toLiveData() = fromPublisher(this)

fun <T : Any> Observable<T>.observeAsLiveData(owner: LifecycleOwner, block: (T) -> Unit) =
    toLiveData().observeValue(owner) {
        if (it != null) {
            block(it)
        }
    }

fun <T : Any> connect(owner: LifecycleOwner, observable: Observable<T>, fn: (T) -> Unit) {
    observable.observeAsLiveData(owner, fn)
}

fun <T : Any> connect(owner: LifecycleOwner, observable: Observable<T>, fn: () -> Unit) {
    observable.observeAsLiveData(owner) { fn() }
}

fun <T> LiveData<T>.observeValue(owner: LifecycleOwner, block: (T) -> Unit) =
    observe(owner) {
        if (it != null) {
            block(it)
        }
    }