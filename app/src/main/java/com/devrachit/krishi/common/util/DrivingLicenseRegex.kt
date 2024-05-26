package com.devrachit.krishi.common.util

fun String.isDrivingLicenseValid(): Boolean {
    if (length != 16) return false
    return true
}