import types.Angle
import types.CleaningDevice
import types.X
import types.Y
import kotlin.math.sin
import kotlin.math.sqrt

class RobotImpl(
    robotData: RobotData,
) : Robot {
    private var currentRobot = robotData

    override fun move(meters: Float) {
        val newPosition = calcNewPosition(currentRobot.position, meters)
        currentRobot = currentRobot.copy(position = newPosition)

        println("POS $newPosition")
    }

    override fun turn(angle: Angle) {
        val newAngle = currentRobot.angle + angle
        currentRobot = currentRobot.copy(
            angle = newAngle,
        )

        println("ANGLE ${currentRobot.angle}")
    }

    override fun set(cleaningDevice: CleaningDevice) {
        currentRobot = currentRobot.copy(
            cleaningDevice = cleaningDevice
        )

        println("STATE ${cleaningDevice.value}")
    }

    override fun start() {
        currentRobot = currentRobot.copy(
            deviceState = DeviceState.ON
        )

        println("START WITH ${currentRobot.cleaningDevice.value}")
    }

    override fun stop() {
        currentRobot = currentRobot.copy(
            deviceState = DeviceState.OFF
        )

        println("STOP")
    }

    private fun calcNewPosition(currentPosition: Pair<X, Y>, moveMeters: Float): Pair<X, Y> {
        val angle = currentRobot.angle.value
        if (angle == 0) {
            return X(currentPosition.first.value + moveMeters) to currentPosition.second
        }

        // прилежащий к гипотенузе катет
        val a = sin(angle.toFloat()) * moveMeters

        // противолежащий к гипотенузе катет
        val b = sqrt(moveMeters.toFloat() - a)

        val newX = currentPosition.first.value + a
        val newY = currentPosition.second.value + b

        return X(newX.round(2)) to Y(newY.round(2))
    }

    fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()
}