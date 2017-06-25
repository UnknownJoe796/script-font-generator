package com.ivieleague.geometry

data class Vector2F(
        var x: Float = 0f,
        var y: Float = 0f
) {

    infix fun equalsRough(other: Vector2F): Boolean = Math.abs(this.x - other.x + this.y - other.y) < .000001

    companion object {
        const val DEG_TO_RAD = (Math.PI / 180).toFloat()
        const val RAD_TO_DEG = (1 / DEG_TO_RAD)
    }

    infix fun distanceTo(other: Vector2F) = distanceTo(other.x, other.y)
    fun distanceTo(x: Float, y: Float): Float {
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    infix fun distanceToSquared(other: Vector2F) = distanceToSquared(other.x, other.y)
    fun distanceToSquared(x: Float, y: Float): Float {
        return x * x + y * y
    }

    var length: Float
        get() = Math.sqrt((x * x + y * y).toDouble()).toFloat()
        set(value) {
            val len = length
            if (len == 0f) {
                x = value
                y = 0f
                return
            }
            val ratio = value / len
            x *= ratio
            y *= ratio
        }

    var lengthSquared: Float
        get() = (x * x + y * y)
        set(value) {
            val len = length
            if (len == 0f) {
                x = value
                y = 0f
                return
            }
            val ratio = Math.sqrt((value / len).toDouble()).toFloat()
            x *= ratio
            y *= ratio
        }

    var angle: Float
        get() = Math.atan2(y.toDouble(), x.toDouble()).toFloat()
        set(value) {
            x = length
            y = 0f
            rotate(value)
        }
    var angleDegrees: Float
        get() = angle * RAD_TO_DEG
        set(value) {
            angle = value * DEG_TO_RAD
        }


    fun normalize(): Vector2F {
        val len = length
        return Vector2F(x / len, y / len)
    }

    fun normalizeAssign() {
        val len = length
        x /= len
        y /= len
    }

    infix fun rotate(radians: Float) {
        val cos = Math.cos(radians.toDouble()).toFloat()
        val sin = Math.sin(radians.toDouble()).toFloat()

        val newX = this.x * cos - this.y * sin
        val newY = this.x * sin + this.y * cos

        this.x = newX
        this.y = newY
    }

    infix fun rotateDegrees(degrees: Float) = rotate(degrees * DEG_TO_RAD)

    fun assign(v: Vector2F): Vector2F {
        x = v.x
        y = v.y
        return this
    }

    fun assign(x: Float, y: Float): Vector2F {
        this.x = x
        this.y = y
        return this
    }

    operator fun plus(other: Vector2F): Vector2F = Vector2F(x + other.x, y + other.y)
    operator fun plusAssign(other: Vector2F) {
        x += other.x; y += other.y
    }

    operator fun minus(other: Vector2F): Vector2F = Vector2F(x - other.x, y - other.y)
    operator fun minusAssign(other: Vector2F) {
        x -= other.x; y -= other.y
    }

    operator fun times(other: Vector2F): Vector2F = Vector2F(x * other.x, y * other.y)
    operator fun timesAssign(other: Vector2F) {
        x *= other.x; y *= other.y
    }

    operator fun times(other: Float): Vector2F = Vector2F(x * other, y * other)
    operator fun timesAssign(other: Float) {
        x *= other; y *= other
    }

    operator fun div(other: Vector2F): Vector2F = Vector2F(x / other.x, y / other.y)
    operator fun divAssign(other: Vector2F) {
        x /= other.x; y /= other.y
    }

    operator fun div(other: Float): Vector2F = Vector2F(x / other, y / other)
    operator fun divAssign(other: Float) {
        x /= other; y /= other
    }

    infix fun cross(other: Vector2F) = x * other.y - y * other.x
    infix fun dot(other: Vector2F) = x * other.x + y * other.y

    fun interpolate(other: Vector2F, alpha: Float): Vector2F {
        val invAlpha = 1.0f - alpha
        return Vector2F(
                x = (x * invAlpha) + (other.x * alpha),
                y = (y * invAlpha) + (other.y * alpha)
        )
    }
}