package com.ivieleague.geometry

data class Vector2D(
        var x: Double = 0.0,
        var y: Double = 0.0
) {

    infix fun equalsRough(other: Vector2D): Boolean = Math.abs(this.x - other.x + this.y - other.y) < .000001

    companion object {
        const val DEG_TO_RAD = (Math.PI / 180)
        const val RAD_TO_DEG = (1 / DEG_TO_RAD)
    }

    infix fun distanceTo(other: Vector2D) = distanceTo(other.x, other.y)
    fun distanceTo(x: Double, y: Double): Double {
        return Math.sqrt((x * x + y * y))
    }

    infix fun distanceToSquared(other: Vector2D) = distanceToSquared(other.x, other.y)
    fun distanceToSquared(x: Double, y: Double): Double {
        return x * x + y * y
    }

    var length: Double
        get() = Math.sqrt((x * x + y * y))
        set(value) {
            val len = length
            if (len == 0.0) {
                x = value
                y = 0.0
                return
            }
            val ratio = value / len
            x *= ratio
            y *= ratio
        }

    var lengthSquared: Double
        get() = (x * x + y * y)
        set(value) {
            val len = length
            if (len == 0.0) {
                x = value
                y = 0.0
                return
            }
            val ratio = Math.sqrt((value / len))
            x *= ratio
            y *= ratio
        }

    var angle: Double
        get() = Math.atan2(y, x)
        set(value) {
            x = length
            y = 0.0
            rotate(value)
        }
    var angleDegrees: Double
        get() = angle * RAD_TO_DEG
        set(value) {
            angle = value * DEG_TO_RAD
        }


    fun normalize(): Vector2D {
        val len = length
        return Vector2D(x / len, y / len)
    }

    fun normalizeAssign() {
        val len = length
        x /= len
        y /= len
    }

    infix fun rotate(radians: Double) {
        val cos = Math.cos(radians)
        val sin = Math.sin(radians)

        val newX = this.x * cos - this.y * sin
        val newY = this.x * sin + this.y * cos

        this.x = newX
        this.y = newY
    }

    infix fun rotateDegrees(degrees: Double) = rotate(degrees * DEG_TO_RAD)

    fun assign(v: Vector2D): Vector2D {
        x = v.x
        y = v.y
        return this
    }

    fun assign(x: Double, y: Double): Vector2D {
        this.x = x
        this.y = y
        return this
    }

    fun plus(otherX: Double, otherY: Double) = Vector2D(x + otherX, y + otherY)
    fun plusAssign(otherX: Double, otherY: Double) {
        x += otherX; y += otherY
    }

    operator fun plus(other: Vector2D): Vector2D = Vector2D(x + other.x, y + other.y)
    operator fun plusAssign(other: Vector2D) {
        x += other.x; y += other.y
    }

    fun minus(otherX: Double, otherY: Double) = Vector2D(x - otherX, y - otherY)
    fun minusAssign(otherX: Double, otherY: Double) {
        x -= otherX; y -= otherY
    }

    operator fun minus(other: Vector2D): Vector2D = Vector2D(x - other.x, y - other.y)
    operator fun minusAssign(other: Vector2D) {
        x -= other.x; y -= other.y
    }

    fun times(otherX: Double, otherY: Double) = Vector2D(x * otherX, y * otherY)
    fun timesAssign(otherX: Double, otherY: Double) {
        x *= otherX; y *= otherY
    }

    operator fun times(other: Vector2D): Vector2D = Vector2D(x * other.x, y * other.y)
    operator fun timesAssign(other: Vector2D) {
        x *= other.x; y *= other.y
    }

    fun div(otherX: Double, otherY: Double) = Vector2D(x / otherX, y / otherY)
    fun divAssign(otherX: Double, otherY: Double) {
        x /= otherX; y /= otherY
    }

    operator fun div(other: Vector2D): Vector2D = Vector2D(x / other.x, y / other.y)
    operator fun divAssign(other: Vector2D) {
        x /= other.x; y /= other.y
    }

    operator fun times(other: Double): Vector2D = Vector2D(x * other, y * other)
    operator fun timesAssign(other: Double) {
        x *= other; y *= other
    }

    operator fun div(other: Double): Vector2D = Vector2D(x / other, y / other)
    operator fun divAssign(other: Double) {
        x /= other; y /= other
    }

    infix fun cross(other: Vector2D) = x * other.y - y * other.x
    infix fun dot(other: Vector2D) = x * other.x + y * other.y

    fun interpolate(other: Vector2D, alpha: Double): Vector2D {
        val invAlpha = 1.0 - alpha
        return Vector2D(
                x = (x * invAlpha) + (other.x * alpha),
                y = (y * invAlpha) + (other.y * alpha)
        )
    }
}