fun main() {
    println("Вводите последовательно команды одна за другой. Для завершения программы введите exit")

    do {
        println("Введите команду:")

        val userCommand = readln()
        val parsedCommand = parseCommand(userCommand)

        if (userCommand == "exit") {
            println("Программа завершается")
            break
        } else if (parsedCommand is Commands.Invalid) {
            println("Программа аварийно завершается")
            break
        }

        println("Команда: $parsedCommand")

    } while (true)
}

private fun parseCommand(userCommand: String): Commands {
    val parts = userCommand.split(" ")
    require(parts.size >= 0 && parts.size < 3) {
        "Программа аварийно завершилась"
    }

    val command = when (userCommand.split(" ").first().lowercase()) {
        "stop" -> Commands.Stop
        "start" -> Commands.Start
        "turn" -> Commands.Turn(parts[1].toInt())
        "set" -> Commands.Set(CleaningDevice.valueOf(parts[1]))
        "move" -> Commands.Move(parts[1].toInt())
        else -> Commands.Invalid
    }

    return command
}