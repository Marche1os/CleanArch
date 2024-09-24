```kotlin
data class RobotCommand(val state: Any, val command: String)

fun transferToCleaner(message: String) {
    println(message)
}

class RobotApi {
    fun processCommand(
        transferToCleaner: (String) -> Unit,
        command: String,
        cleanerState: Any
    ) {
        val cmdParts = command.split(" ")
        return when (cmdParts[0]) {
            "move" -> {
                val arg = cmdParts.getOrNull(1)?.toIntOrNull() ?: 0
                transferToCleaner("move $arg")
                cleanerState
            }
            "turn" -> {
                val arg = cmdParts.getOrNull(1)?.toIntOrNull() ?: 0
                transferToCleaner("turn $arg")
                cleanerState
            }
            "set" -> {
                val arg = cmdParts.getOrNull(1) ?: ""
                transferToCleaner("set $arg")
                cleanerState
            }
            "start" -> {
                transferToCleaner("start")
                cleanerState
            }
            "stop" -> {
                transferToCleaner("stop")
                cleanerState
            }
            else -> {
                transferToCleaner("Неизвестная команда: $command")
                cleanerState
            }
        }
    }
}
```