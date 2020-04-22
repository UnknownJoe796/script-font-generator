package com.ivieleague.fontgenerator.rune

import com.ivieleague.bitIndexSequence
import com.ivieleague.toInt

/**
 * Created by josep on 6/24/2017.
 */

typealias Rune = Int

fun Rune.runeLineSequence(type: RuneType): Sequence<Pair<com.ivieleague.geometry.Vector2D, com.ivieleague.geometry.Vector2D>> = this.bitIndexSequence().map { type[it] }
fun Rune.runeFullyConnected(runeType: RuneType): Boolean {
    val items = this.bitIndexSequence().toSet()
    if (items.isEmpty()) return true
    val connectedToFirst = HashSet<Rune>()
    val connections = runeType.connections()
    fun addConnections(from: Rune) {
        if (from !in items) return
        if (from in connectedToFirst) return
        connectedToFirst.add(from)
        connections[from].forEach { addConnections(it) }
    }
    addConnections(items.first())
    return items.size == connectedToFirst.size
}

fun Rune.runeIsSingleLineDrawable(runeType: RuneType): Boolean = this.bitIndexSequence()
        .flatMap { sequenceOf(runeType[it].first, runeType[it].second) }
        .frequencyMap()
        .count { it.value % 2 == 1 } <= 2

fun Rune.runeMirrorHorizontal(runeType: RuneType): Rune? {
    return this.bitIndexSequence().toList().map { runeType.segmentIndexMirrorHorizontal(it).takeIf { it != -1 } ?: return null }.toInt()
}

fun Rune.runeMirrorVertical(runeType: RuneType): Rune? {
    return this.bitIndexSequence().toList().map { runeType.segmentIndexMirrorVertical(it).also { if (it == -1) return null } }.toInt()
}

fun Rune.runeMirrorBoth(runeType: RuneType): Rune? {
    return this.bitIndexSequence().toList().map { runeType.segmentIndexMirrorBoth(it).also { if (it == -1) return null } }.toInt()
}