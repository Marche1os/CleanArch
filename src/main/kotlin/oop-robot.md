```kotlin
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


/**
 * Принимает комманду, введенную пользователем, и возвращает алгебраический тип данных - команда, которую понимает робот
 */
interface CommandParser {
    fun parse(userInput: String): RobotCommand
}

class CommandParserImpl : CommandParser {
    override fun parse(rawUserInput: String): RobotCommand = runCatching {
        val parts = rawUserInput.lowercase().split(" ")

        when (parts.first()) {
            "move" -> parseMoveCommand(parts[1])
            "turn" -> parseTurnCommand(parts[1])
            "set" -> parseSetCommand(parts[1])
            "start" -> RobotCommand.Start
            "stop" -> RobotCommand.Stop
            else -> RobotCommand.Invalid
        }

    }.getOrElse { RobotCommand.Invalid }

    private fun parseMoveCommand(value: String): RobotCommand.Move {
        val moveInMeters = MoveMeters(value.toFloat())
        return RobotCommand.Move(moveInMeters)
    }

    private fun parseTurnCommand(value: String): RobotCommand.Turn {
        val turnAngle = Angle(value.toInt())
        return RobotCommand.Turn(turnAngle)
    }

    private fun parseSetCommand(value: String): RobotCommand.Set {
        val cleaningDevice = CleaningDevice.valueOf(value.lowercase())
        return RobotCommand.Set(cleaningDevice)
    }

}

sealed interface RobotCommand {

    data class Move(val meters: MoveMeters) : RobotCommand

    data class Turn(val angle: Angle) : RobotCommand

    data class Set(val cleaningDevice: CleaningDevice) : RobotCommand

    object Start : RobotCommand

    object Stop : RobotCommand

    object Invalid : RobotCommand
}

/**
 * Значение движения (в метрах)
 */
@JvmInline
value class MoveMeters(val value: Float)

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


/**
 * Отвечает за перемещение робота. В данный момент реализовано перемещение по плоскости. Возможно в дальнейшем перемещение по 3D пространству
 */
interface RobotMovement {

    fun move(oldPosition: Position, currentAngle: Angle, distance: MoveMeters): Position
}

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

```