package com.ivieleague.fontgenerator.rune

import com.ivieleague.bits
import java.util.*
import kotlin.collections.ArrayList

fun Set<Rune>.minimizeSimilaritiesSubsetAlternate(runeType: RuneType, count: Int): Set<Rune> {
    val from = this.toMutableList()
    Collections.shuffle(from)
    val new = HashSet<Rune>()
    repeat(count) {
        val (index, rune) = from.asSequence().mapIndexed { index, i -> index to i }.flatMap { (index, rune) ->
            sequenceOf(
                    index to rune,
                    index to rune.runeMirrorHorizontal(runeType),
                    index to rune.runeMirrorVertical(runeType),
                    index to rune.runeMirrorBoth(runeType)
            ).mapNotNull { if (it.second == null) null else it.first to it.second!! }
        }.minBy { (_, rune) ->
            new.asSequence().map { other -> (rune xor other).bits().take(runeType.size).count { !it } }.max() ?: 0
        }!!
        from.removeAt(index)
        new += rune
    }
    return new
}

fun Set<Rune>.minimizeSimilaritiesOrder(runeType: RuneType): List<Rune> {
    val from = this.toMutableList()
    Collections.shuffle(from)
    val new = ArrayList<Rune>()
    while (from.isNotEmpty()) {
        val (index, rune) = from.asSequence().mapIndexed { index, i -> index to i }.flatMap { (index, rune) ->
            sequenceOf(
                    index to rune,
                    index to rune.runeMirrorHorizontal(runeType),
                    index to rune.runeMirrorVertical(runeType),
                    index to rune.runeMirrorBoth(runeType)
            ).mapNotNull { if (it.second == null) null else it.first to it.second!! }
        }.minBy { (_, rune) ->
            new.asSequence().map { other -> (rune xor other).bits().take(runeType.size).count { !it } }.max() ?: 0
        }!!
        from.removeAt(index)
        new += rune
    }
    return new
}