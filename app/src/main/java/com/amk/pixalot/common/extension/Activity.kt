package com.amk.pixalot.common.extension

import android.app.Activity

fun Activity?.getAndRemoveStringExtra(key: String): String {
    return this?.let { act ->
        if (act.intent.hasExtra(key)) {
            act.intent?.extras?.getString(key, "")
                    .also { act.intent.removeExtra(key) }
        } else ""
    } ?: ""
}