package com.ivieleague.fontgenerator

import com.ivieleague.bitIndexSequence
import com.ivieleague.fontasticalternative.Font
import com.ivieleague.fontasticalternative.buildTypeface
import com.ivieleague.fontgenerator.rune.*
import com.ivieleague.geometry.Vector2D
import com.ivieleague.getBit
import org.doubletype.ossa.Engine
import java.io.File

fun main(vararg args: String) {

    val runes = generateRunes().toList()

    println(runes.size)
    println(runes.joinToString("\n\n") { renderHexRune(it) + " " + it.bitIndexSequence().joinToString() })

    fun List<List<Vector2D>>.toMyFontGlyph() = toFontGlyph(
            inputWidth = 0.5,
            inputHeight = 1.0,
            outputWidth = 500.0,
            outputHeight = 1000.0,
            additionalSpacing = 200.0
    )

    val rangeMaps = listOf(
            'A'..'Z',
            '0'..'9',
            '!'..'/',
            ':'..'@',
            '['..'`',
            '{'..'~'
    )

    val charToRune = rangeMaps.flatMap {
        it.mapIndexedNotNull { index, char ->
            if (index < runes.size) char to runes[index]
            else null
        }
    }.toMap()

    fun Rune.toGlyph() = runeToHorizStroke(type = RuneTypes.hexRune, thickness = .1).toMyFontGlyph()
    fun Rune.toShortGlyph() = runeToHorizStroke(type = RuneTypes.hexRune, thickness = .1).map { it.map { it.times(1.0, .5) } }.toMyFontGlyph()

    val charToGlyph = charToRune.mapValues { it.value.toGlyph() } + charToRune.entries.asSequence()
            .filter { it.key in 'A'..'Z' }
            .associate { it.key.plus('a' - 'A') to it.value.toShortGlyph() }

    val font = Font(
            name = "Hex Runes",
            author = "Unknown",
            version = "0.3",
            baseline = 0f,
            meanline = 200f,
            glyphs = charToGlyph
    )

    Engine.getSingletonInstance().buildTypeface(font, File("./build/working").apply { mkdirs() })
}

private fun generateRunes(): List<Rune> {
    val type = RuneTypes.hexRune
    val runes = type.possibilities().asSequence()
            .filter { it.runeFullyConnected(type) }
            .filter { it <= it.runeMirrorHorizontal(type) ?: Int.MAX_VALUE }
            .filter { it <= it.runeMirrorVertical(type) ?: Int.MAX_VALUE }
            .filter { it <= it.runeMirrorBoth(type) ?: Int.MAX_VALUE }
            .filter { it.bitIndexSequence().count() in 1..6 }
            .filter { it.runeIsSingleLineDrawable(type) }
            .filter { ((it.getBit(0) || it.getBit(5)) && (it.getBit(3) || it.getBit(2))) || (it.getBit(1) && it.getBit(4)) }
            .toSet()
            .minimizeSimilaritiesOrder(type)
    return runes
}

fun renderHexRune(input: Rune): String {
    return ("  T  " + "\n" +
            " 560 " + "\n" +
            "L 6 R" + "\n" +
            "4B671" + "\n" +
            "4 C 1" + "\n" +
            "4A981" + "\n" +
            "D 9 E" + "\n" +
            " 392 " + "\n" +
            "  O  ")
            .replace("0", if (input.getBit(0)) "X" else " ")
            .replace("1", if (input.getBit(1)) "X" else " ")
            .replace("2", if (input.getBit(2)) "X" else " ")
            .replace("3", if (input.getBit(3)) "X" else " ")
            .replace("4", if (input.getBit(4)) "X" else " ")
            .replace("5", if (input.getBit(5)) "X" else " ")
            .replace("6", if (input.getBit(6)) "X" else " ")
            .replace("7", if (input.getBit(7)) "X" else " ")
            .replace("8", if (input.getBit(8)) "X" else " ")
            .replace("9", if (input.getBit(9)) "X" else " ")
            .replace("A", if (input.getBit(10)) "X" else " ")
            .replace("B", if (input.getBit(11)) "X" else " ")
            .replace("T", if (input.getBit(0) || input.getBit(5) || input.getBit(6)) "X" else " ")
            .replace("O", if (input.getBit(2) || input.getBit(3) || input.getBit(9)) "X" else " ")
            .replace("L", if (input.getBit(5) || input.getBit(4) || input.getBit(11)) "X" else " ")
            .replace("R", if (input.getBit(0) || input.getBit(1) || input.getBit(7)) "X" else " ")
            .replace("D", if (input.getBit(3) || input.getBit(4) || input.getBit(10)) "X" else " ")
            .replace("E", if (input.getBit(1) || input.getBit(2) || input.getBit(8)) "X" else " ")
            .replace("C", if ((6..11).any { input.getBit(it) }) "X" else " ")
}