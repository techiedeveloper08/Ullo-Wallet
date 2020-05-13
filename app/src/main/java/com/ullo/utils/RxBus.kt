package com.ullo.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {

    private val bus = PublishSubject.create<Any>()

    @Synchronized
    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }
}