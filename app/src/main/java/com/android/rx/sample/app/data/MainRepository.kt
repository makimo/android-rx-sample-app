package com.android.rx.sample.app.data

import com.android.rx.sample.app.utils.defaultBehavior
import com.android.rx.sample.app.utils.publishSubject
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class MainRepository {

    val counter = defaultBehavior(0L)

    init {
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribe {
                counter.onNext(it)
            }
    }
}