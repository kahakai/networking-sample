@file:Suppress("NOTHING_TO_INLINE")

package com.artnest.networkingsample.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Context.toast(@StringRes resId: Int) =
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

inline fun Fragment.toast(@StringRes resId: Int) =
    requireContext().toast(resId)
