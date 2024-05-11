package com.devrachit.krishi.common.util


fun String.isValidOtp(): Boolean {
    if (length != 6) return false
    return true
}