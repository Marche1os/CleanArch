import types.Angle
import types.CleaningDevice
import types.X
import types.Y

fun main() {
    Main().startProgram()
}

class Main {
    val robot: Robot = RobotImpl(
        RobotData(
            position = X(0f) to Y(0f),
            angle = Angle(0),
            cleaningDevice = CleaningDevice.WATER,
        )
    )

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
}
