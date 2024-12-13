package utils

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

infix fun Long.concat(other: Long): Long {
    val power = floor(log10(other.toDouble())) + 1

    return (this * 10.0.pow(power)).toLong() + other
}