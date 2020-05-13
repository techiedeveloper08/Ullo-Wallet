package com.ullo.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


object RxSearch {

    fun fromView(searchView: EditText): Observable<String> {

        val subject = PublishSubject.create<String>()

        searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                subject.onNext(text.toString())
            }

        })

        return subject
    }
}