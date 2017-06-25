package com.ivieleague.geometry

fun Vector2D.clockwise(first: Vector2D, second: Vector2D): Boolean
        = (first.y - this.x) * (second.x - first.x) - (first.x - this.x) * (second.y - first.y) > 0.0

fun List<Vector2D>.lineSequence(): Sequence<Pair<Vector2D, Vector2D>> = indices.asSequence().map {
    this[it] to this[it.plus(1).rem(size)]
}

fun List<Vector2D>.area(): Double = lineSequence().sumByDouble { (it.second.x - it.first.x) * (it.second.y + it.first.y) }
fun List<Vector2D>.clockwise(): Boolean = area() > 0.0