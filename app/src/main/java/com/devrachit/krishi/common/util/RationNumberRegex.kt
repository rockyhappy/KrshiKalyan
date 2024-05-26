package com.devrachit.krishi.common.util

fun String.isRationNumberValid(): Boolean {
    if (length != 0) return false
    if (!all { it.isDigit() }) return false
    return true
}