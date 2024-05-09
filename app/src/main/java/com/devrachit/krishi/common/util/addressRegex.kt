package com.devrachit.krishi.common.util

fun String.address(): Boolean {
    if (length <= 6) return false
    return true
}
