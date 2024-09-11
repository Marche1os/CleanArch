import types.Angle
import types.CleaningDevice
import types.MoveMeters

sealed interface RobotCommand {

    data class Move(val meters: MoveMeters) : RobotCommand

    data class Turn(val angle: Angle) : RobotCommand

    data class Set(val cleaningDevice: CleaningDevice) : RobotCommand

    object Start : RobotCommand

    object Stop : RobotCommand

    object Invalid : RobotCommand
}