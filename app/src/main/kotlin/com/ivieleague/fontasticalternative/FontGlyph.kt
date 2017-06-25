package com.ivieleague.fontasticalternative

/**
 * Created by josep on 6/23/2017.
 */
data class FontGlyph(
        var contours: List<List<FontPoint>> = listOf(),
        var advanceWidth: Int = 600
)