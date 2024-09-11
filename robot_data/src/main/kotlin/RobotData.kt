package types

data class RobotData(
    val position: Position,
    val angle: Angle,
    val cleaningDevice: CleaningDevice,
    val deviceState: DeviceState = DeviceState.OFF
)

enum class DeviceState {
    ON,
    OFF
}

