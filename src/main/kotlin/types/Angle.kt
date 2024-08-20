package types

@JvmInline
value class Angle(val value: Int) {

    init {
        require(value >= -360 && value <= 360)
    }
}