package uz.infinity.app.utils

import timber.log.Timber

fun <T> T.myApply(block : T.() -> Unit) {
    block(this)
}

fun myLog(message : String, tag : String = "TTT") {
    Timber.tag(tag).d(message)
}


