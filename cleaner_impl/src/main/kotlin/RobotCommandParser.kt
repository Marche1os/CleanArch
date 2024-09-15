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