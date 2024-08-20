import types.Angle
import types.CleaningDevice
import types.X
import types.Y

data class RobotData(
    val position: Pair<X, Y>,
    val angle: Angle,
    val cleaningDevice: CleaningDevice,
    val deviceState: DeviceState = DeviceState.OFF
)

enum class DeviceState {
    ON,
    OFF
}

