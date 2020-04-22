package com.ivieleague.fontgenerator

import com.ivieleague.fontasticalternative.FontGlyph
import com.ivieleague.fontasticalternative.toFontPoint
import com.ivieleague.fontgenerator.rune.Rune
import com.ivieleague.fontgenerator.rune.RuneType
import com.ivieleague.fontgenerator.rune.runeLineSequence
import com.ivieleague.geometry.Vector2D
import com.ivieleague.geometry.clockwise

/**
 * Created by josep on 6/24/2017.
 */

private val rightByABit = Vector2D(.05, 0.0)
private val leftByABit = Vector2D(-.05, 0.0)

fun Rune.runeToHorizStroke(type: RuneType, thickness: Double = .05) = this.runeLineSequence(type).map {
    rightByABit.x = thickness
    leftByABit.x = -thickness
    listOf(
            it.first + leftByABit,
            it.second + leftByABit,
            it.second + rightByABit,
            it.first + rightByABit
    ).let {
        if (it.clockwise())
            it
        else
            it.reversed()
    }
}.toList()

fun List<List<Vector2D>>.toFontGlyph(
        inputWidth: Double = this.asSequence().flatMap { it.asSequence() }.map { Math.abs(it.x) }.max() ?: 1.0,
        inputHeight: Double = this.asSequence().flatMap { it.asSequence() }.map { Math.abs(it.y) }.max() ?: 1.0,
        outputWidth: Double = 300.0,
        outputHeight: Double = 600.0,
        additionalSpacing: Double = outputWidth.times(.2)
): FontGlyph = FontGlyph(
        contours = this.map {
            it.map {
                it.plus(inputWidth, inputHeight).times(.5 / inputWidth, .5 / inputHeight).times(outputWidth, outputHeight).toFontPoint()
            }
        }.toList(),
        advanceWidth = outputWidth.toInt() + additionalSpacing.toInt()
)