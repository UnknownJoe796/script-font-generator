package com.ivieleague.fontgenerator.rune

import com.ivieleague.geometry.Vector2D

object RuneTypes {
    private val hexRuneVertices = listOf(
            Vector2D(0.0, 1.0),
            Vector2D(0.5, 0.5),
            Vector2D(0.5, -.5),
            Vector2D(0.0, -1.0),
            Vector2D(-.5, -.5),
            Vector2D(-.5, 0.5),
            Vector2D(0.0, 0.0)
    )
    val hexRune: RuneType = listOf(
            0 to 1,
            1 to 2,
            2 to 3,
            3 to 4,
            4 to 5,
            5 to 0,
            6 to 0,
            6 to 1,
            6 to 2,
            6 to 3,
            6 to 4,
            6 to 5
    ).map { (a, b) -> hexRuneVertices[a] to hexRuneVertices[b] }
}