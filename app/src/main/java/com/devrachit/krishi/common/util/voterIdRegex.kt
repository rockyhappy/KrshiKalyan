package com.devrachit.krishi.common.util

fun String.isVoterIdValid(): Boolean {
    if (length != 10) return false
    if (!all { it.isDigit() }) return false
    return true
}