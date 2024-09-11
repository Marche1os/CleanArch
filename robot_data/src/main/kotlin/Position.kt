package types

data class Position(
    val x: X,
    val y: Y
)

@JvmInline
value class X(val value: Float) {
    override fun toString(): String {
        return "x=$value"
    }
}

@JvmInline
value class Y(val value: Float) {
    override fun toString(): String {
        return "y=$value"
    }
}