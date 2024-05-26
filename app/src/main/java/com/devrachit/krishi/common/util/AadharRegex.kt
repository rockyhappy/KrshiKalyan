package com.devrachit.krishi.common.util

fun String.isAadharValid(): Boolean {
    if (length != 12) return false
    if (!all { it.isDigit() }) return false
    return true
}