package com.android.rx.sample.app.utils

import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import com.android.rx.sample.app.App

fun context() = App.instance

fun string(resId: Int) = context().resources.getString(resId)

fun formatString(resId: Int, vararg formatArgs: Any?) =
    context().resources.getString(resId, *formatArgs)

fun TextView.format(resId: Int, vararg formatArgs: Any?) {
    text = formatString(resId, *formatArgs)
}

fun View.click(listener: () -> Unit) {
    setOnClickListener { listener() }
}

fun CompoundButton.toggleClick(listener: (CompoundButton, Boolean) -> Unit) {
    setOnCheckedChangeListener { view, isChecked ->
        listener(view, isChecked)
    }
}