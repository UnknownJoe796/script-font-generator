package com.ivieleague.fontasticalternative

import org.doubletype.ossa.Engine
import org.doubletype.ossa.adapter.EContour
import org.doubletype.ossa.adapter.EContourPoint
import org.doubletype.ossa.adapter.EControlPoint
import java.io.File


data class Font(
        var glyphs: Map<Char, FontGlyph> = mapOf(),
        var name: String = "",
        var author: String = "",
        var copyrightYear: String = "2017",
        var version: String = "0.1",
        var license: String = "CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/",
        var baseline: Float = 0f,
        var meanline: Float = 300f,
        var defaultAdvanceWidth: Int = 600,
        var topSideBearing: Float = 170f,
        var bottomSideBearing: Float = 0f,
        var ascender: Float = 683f,
        var descender: Float = 171f,
        var xHeight: Float = 0f
)

fun Engine.buildTypeface(font: Font, file: File) {
    buildNewTypeface(font.name.filter { it.isLetterOrDigit() }, file)

    setAuthor(font.author)
    setCopyrightYear(font.copyrightYear)
    typeface.glyph.head.version = font.version
    setFontFamilyName(font.name)
    setTypefaceLicense(font.license)
    setBaseline(font.baseline.toDouble())
    setMeanline(font.meanline.toDouble())
    setAdvanceWidth(font.defaultAdvanceWidth)
    typeface.topSideBearing = font.topSideBearing.toDouble()
    typeface.bottomSideBearing = font.bottomSideBearing.toDouble()
    typeface.ascender = font.ascender.toDouble()
    typeface.descender = font.descender.toDouble()
    typeface.xHeight = font.xHeight.toDouble()

    addDefaultGlyphs()

    for ((char, glyph) in font.glyphs) {
        val glyphFile = addNewGlyph(char.toLong())
        glyphFile.advanceWidth = glyph.advanceWidth
        glyph.contours.map { contour ->
            EContour().apply {
                type = EContour.k_cubic
                contour.map {
                    EContourPoint(it.point.x, it.point.y, true).apply {
                        val a = it.controlA
                        if (a != null)
                            controlPoint1 = EControlPoint(true, a.x, a.y)
                        val b = it.controlB
                        if (b != null)
                            controlPoint2 = EControlPoint(false, b.x, b.y)
                    }
                }.forEach {
                    addContourPoint(it)
                }
            }
        }.forEach {
            glyphFile.addContour(it)
        }
        glyphFile.saveGlyphFile()
    }

    setAuthor(font.author)
    setCopyrightYear(font.copyrightYear)
    typeface.glyph.head.version = font.version
    setFontFamilyName(font.name)
    setTypefaceLicense(font.license)
    setBaseline(font.baseline.toDouble())
    setMeanline(font.meanline.toDouble())
    setAdvanceWidth(font.defaultAdvanceWidth)
    typeface.topSideBearing = font.topSideBearing.toDouble()
    typeface.bottomSideBearing = font.bottomSideBearing.toDouble()
    typeface.ascender = font.ascender.toDouble()
    typeface.descender = font.descender.toDouble()
    typeface.xHeight = font.xHeight.toDouble()

    buildTrueType(false)
}