package com.ivieleague.fontgenerator.rune

import com.ivieleague.geometry.Vector2D

typealias RuneType = List<Pair<Vector2D, Vector2D>>

fun RuneType.possibilities() = (0..1.shl(this.size).minus(1))
fun RuneType.connections() = this.indices.map { a ->
    this.indices.filter { b ->
        val segA = this[a]
        val segB = this[b]
        segA.first == segB.first || segA.first == segB.second || segA.second == segB.first || segA.second == segB.second
    }
}

private val epsilon = .0001
fun RuneType.segmentIndexMirrorHorizontal(input: Int): Int {
    val original = this[input]
    return this.indexOfFirst {
        (
                Math.abs(it.first.x - (-original.first.x)) +
                        Math.abs(it.first.y - (original.first.y)) +
                        Math.abs(it.second.x - (-original.second.x)) +
                        Math.abs(it.second.y - (original.second.y))
                ) < epsilon ||
                (
                        Math.abs(it.first.x - (-original.second.x)) +
                                Math.abs(it.first.y - (original.second.y)) +
                                Math.abs(it.second.x - (-original.first.x)) +
                                Math.abs(it.second.y - (original.first.y))
                        ) < epsilon
    }
}

fun RuneType.segmentIndexMirrorVertical(input: Int): Int {
    val original = this[input]
    return this.indexOfFirst {
        (
                Math.abs(it.first.x - (original.first.x)) +
                        Math.abs(it.first.y - (-original.first.y)) +
                        Math.abs(it.second.x - (original.second.x)) +
                        Math.abs(it.second.y - (-original.second.y))
                ) < epsilon ||
                (
                        Math.abs(it.first.x - (original.second.x)) +
                                Math.abs(it.first.y - (-original.second.y)) +
                                Math.abs(it.second.x - (original.first.x)) +
                                Math.abs(it.second.y - (-original.first.y))
                        ) < epsilon
    }
}

fun RuneType.segmentIndexMirrorBoth(input: Int): Int {
    val original = this[input]
    return this.indexOfFirst {
        (
                Math.abs(it.first.x - (-original.first.x)) +
                        Math.abs(it.first.y - (-original.first.y)) +
                        Math.abs(it.second.x - (-original.second.x)) +
                        Math.abs(it.second.y - (-original.second.y))
                ) < epsilon ||
                (
                        Math.abs(it.first.x - (-original.second.x)) +
                                Math.abs(it.first.y - (-original.second.y)) +
                                Math.abs(it.second.x - (-original.first.x)) +
                                Math.abs(it.second.y - (-original.first.y))
                        ) < epsilon
    }
}

fun <T> Sequence<T>.frequencyMap(): Map<T, Int> = HashMap<T, Int>().also {
    for (item in this) {
        it[item] = it.getOrDefault(item, 0) + 1
    }
}