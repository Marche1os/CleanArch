import kotlin.math.cos
import kotlin.math.sin

class RobotMovementImpl : RobotMovement {
    override fun move(oldPosition: Position, currentAngle: Angle, distance: MoveMeters): Position {
        val newPosition = calcNewPosition(oldPosition, currentAngle, distance.value)

        return newPosition
    }

    private fun calcNewPosition(currentPosition: Position, currentAngle: Angle, moveMeters: Float): Position {
        val angleRad = currentAngle.value * (Math.PI / 180.0)

        val x = cos(angleRad) * moveMeters
        val y = sin(angleRad) * moveMeters

        val newX = currentPosition.x.value + x
        val newY = currentPosition.y.value + y

        return Position(
            x = X(newX.toFloat().round(2)),
            y = Y(newY.toFloat().round(2)),
        )
    }

    fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()
}