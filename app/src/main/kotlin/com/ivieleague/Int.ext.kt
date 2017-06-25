package com.ivieleague

/**
 * Created by josep on 6/23/2017.
 */

fun Int.bitIndexSequence(): Sequence<Int> = (0..31).asSequence().filter { this.getBit(it) }

fun Int.getBit(index: Int): Boolean = (this ushr index) and 0x1 != 0
fun Int.setBit(index: Int, value: Boolean): Int {
    if (value) {
        return this or (0x1 shl index)
    } else {
        return this and (0x1 shl index).inv()
    }
}

fun Int.bits(): Sequence<Boolean> = (0..31).asSequence().map { this.getBit(it) }

fun Sequence<Int>.toInt(): Int {
    var current = 0x0
    for (bitIndex in this) {
        current = current.setBit(bitIndex, true)
    }
    return current
}

fun Collection<Int>.toInt(): Int = asSequence().toInt()