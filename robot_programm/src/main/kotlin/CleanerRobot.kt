import types.Angle
import types.CleaningDevice
import types.DeviceState
import types.MoveMeters
import types.RobotData

interface CleanerRobot {

    fun performCommand(command: RobotCommand)
}

class CleanerRobotImpl(
    robotData: RobotData,
    private val movement: RobotMovement,
) : CleanerRobot {
    private var currentRobot = robotData

    override fun performCommand(command: RobotCommand) {
        when (command) {
            is RobotCommand.Move -> move(command.meters)
            is RobotCommand.Turn -> turn(command.angle)
            is RobotCommand.Set -> set(command.cleaningDevice)
            is RobotCommand.Start -> start()
            is RobotCommand.Stop -> stop()
            is RobotCommand.Invalid -> {}
        }
    }

    private fun move(moveMeters: MoveMeters) {
        val newPosition = movement.move(currentRobot.position, currentRobot.angle, moveMeters)
        currentRobot = currentRobot.copy(position = newPosition)

        println("POS $newPosition")
    }

    private fun turn(angle: Angle) {
        val newAngle = currentRobot.angle + angle
        currentRobot = currentRobot.copy(
            angle = newAngle,
        )

        println("ANGLE ${currentRobot.angle}")
    }

    private fun set(cleaningDevice: CleaningDevice) {
        currentRobot = currentRobot.copy(
            cleaningDevice = cleaningDevice
        )

        println("STATE ${cleaningDevice.value}")
    }

    private fun start() {
        currentRobot = currentRobot.copy(
            deviceState = DeviceState.ON
        )

        println("START WITH ${currentRobot.cleaningDevice.value}")
    }

    private fun stop() {
        currentRobot = currentRobot.copy(
            deviceState = DeviceState.OFF
        )

        println("STOP")
    }
}