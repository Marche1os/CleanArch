import types.Angle
import types.CleaningDevice
import types.X
import types.Y

data class RobotInfo(
    val position: Pair<X, Y>,
    val angle: Angle,
    val cleaningDevice: CleaningDevice,
)


