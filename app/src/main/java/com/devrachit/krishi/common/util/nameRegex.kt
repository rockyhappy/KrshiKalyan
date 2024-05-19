package com.devrachit.krishi.common.util

fun String.isLongAndLettersOnly(): Boolean {
    if (length <= 4) return false
    if (!all { it.isLetter() || it.isWhitespace() }) return false
    return true
}
