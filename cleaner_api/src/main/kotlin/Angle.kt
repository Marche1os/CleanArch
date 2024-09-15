@JvmInline
value class Angle(val value: Int) {

    init {
        require(value >= -360 && value <= 360)
    }

    override fun toString(): String {
        return value.toString()
    }

    operator fun plus(other: Angle): Angle {
        return Angle(value + other.value)
    }
}