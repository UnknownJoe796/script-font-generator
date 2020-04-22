package com.ivieleague.fontgenerator.test

import com.ivieleague.fontasticalternative.Font
import com.ivieleague.fontasticalternative.FontGlyph
import com.ivieleague.fontasticalternative.buildTypeface
import com.ivieleague.fontasticalternative.toFontPoint
import com.ivieleague.geometry.Vector2F
import lk.kotlin.jvm.utils.random.random
import org.doubletype.ossa.Engine
import java.io.File

fun main(vararg args: String) {
    val font = Font(
            name = "The Font of Chaos",
            author = "Unknown",
            glyphs = (('a'..'z') + ('A'..'Z')).associate {
                it to FontGlyph(contours = listOf(listOf(
                        Vector2F(0f, 0f).toFontPoint(),
                        Vector2F(512f, 0f).toFontPoint(),
                        Vector2F(512f, (50f..512f).random()).toFontPoint(),
                        Vector2F(0f, (50f..512f).random()).toFontPoint()
                )))
            }
    )

    Engine.getSingletonInstance().buildTypeface(font, File("./build/working").apply { mkdirs() })
}