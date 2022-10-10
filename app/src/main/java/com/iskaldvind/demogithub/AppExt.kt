package com.iskaldvind.demogithub

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.arguments(vararg arguments: Pair<String, Any>): Fragment {
    this.arguments = bundleOf(*arguments)
    return this
}

fun showErrorSnack(view: View, message: String, color: Int) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(color)
        show()
    }
}