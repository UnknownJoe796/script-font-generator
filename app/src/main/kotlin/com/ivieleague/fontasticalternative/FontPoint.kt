package com.ivieleague.fontasticalternative

import com.ivieleague.geometry.Vector2D
import com.ivieleague.geometry.Vector2F

/**
 * Created by josep on 6/23/2017.
 */
data class FontPoint(
        var point: Vector2D = Vector2D(),
        var controlA: Vector2D? = null,
        var controlB: Vector2D? = null
)

fun Vector2D.toFontPoint(): FontPoint = FontPoint(this)
fun Vector2F.toFontPoint(): FontPoint = FontPoint(Vector2D(x.toDouble(), y.toDouble()))