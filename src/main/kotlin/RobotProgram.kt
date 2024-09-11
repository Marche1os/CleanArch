import types.Angle
import types.CleaningDevice
import types.DeviceState
import types.Position
import types.RobotData
import types.X
import types.Y


class RobotProgram {
    private lateinit var cleanerRobot: CleanerRobot
    private lateinit var commandParser: CommandParser

    fun main() {
        init()

        do {
            println("Введите команду:")

            val userCommand = readln()
            val processingResult = runCatching {
                commandParser.parse(userCommand)
            }.onSuccess { command -> cleanerRobot.performCommand(command) }

            if (processingResult.isFailure) {
                println("Из-за ошибки программа вынужденно аварийно завершиться")
                break
            }

            if (userCommand == "exit") {
                println("Программа завершается")
                break
            }

        } while (true)
    }

    private fun init() {
        cleanerRobot = CleanerRobotImpl(
            robotData = RobotData(
                position = Position(
                    x = X(0f),
                    y = Y(0f),
                ),
                angle = Angle(0),
                cleaningDevice = CleaningDevice.WATER,
                deviceState = DeviceState.OFF,
            ),
            movement = RobotMovementImpl(),
        )

        commandParser = CommandParserImpl()
    }
}