```kotlin
 fun startProgram() {
        println("Вводите последовательно команды одна за другой. Для завершения программы введите exit")

        do {
            println("Введите команду:")

            val userCommand = readln()
            val processingResult = runCatching {
                processCommand(userCommand)
            }

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

    private fun processCommand(userInputCommand: String) {
        val parts = userInputCommand.lowercase().split(" ")

        when (parts.first()) {
            "move" -> processMove(parts[1])
            "turn" -> processTurn(parts[1])
            "set" -> processSet(parts[1])
            "start" -> processStart()
            "stop" -> processStop()
            else -> println("Неизвестная команда. Попробуйте повторить.")
        }
    }

    private fun processMove(value: String) {
        val meters = value.toFloat()
        robot.move(meters)
    }

    private fun processTurn(value: String) {
        robot.turn(Angle(value.toInt()))
    }

    private fun processSet(value: String) {
        val device = CleaningDevice.entries
            .find { it.value == value }
            ?: CleaningDevice.WATER

        robot.set(device)
    }

    private fun processStart() {
        robot.start()
    }

    private fun processStop() {
        robot.stop()
    }
```

```kotlin
import kotlin.math.cos
import kotlin.math.sin

class RobotImpl(
    robotData: RobotData,
) : RobotV2 {
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
        val angleRad = currentRobot.angle.value * (Math.PI / 180.0)

        val x = cos(angleRad) * moveMeters
        val y = sin(angleRad) * moveMeters

        val newX = currentPosition.first.value + x
        val newY = currentPosition.second.value + y

        return X(newX.round(2)) to Y(newY.round(2))
    }

    fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()
}
```