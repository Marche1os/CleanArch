```kotlin
data class RobotCommand(val state: Any, val command: String)

fun transferToCleaner(message: String) {
    println(message)
}

val queue: BlockingQueue<RobotCommand> = LinkedBlockingQueue()
val outQueue: BlockingQueue<RobotCommand> = LinkedBlockingQueue()

class ThreadRobo(
    private val queue: BlockingQueue<RobotCommand>,
    private val outQueue: BlockingQueue<RobotCommand>,
    private val moveFunc: (transferToCleaner: (String) -> Unit, arg: Int, cleanerState: Any) -> Unit,
    private val turnFunc: (transferToCleaner: (String) -> Unit, arg: Int, cleanerState: Any) -> Unit,
    private val setStateFunc: (transferToCleaner: (String) -> Unit, arg: String, cleanerState: Any) -> Unit,
    private val startFunc: (transferToCleaner: (String) -> Unit, cleanerState: Any) -> Unit,
    private val stopFunc: (transferToCleaner: (String) -> Unit, cleanerState: Any) -> Unit
) : Thread() {

    override fun run() {
        while (true) {
            val command = queue.take()
            var cleanerState = command.state
            val cmd = command.command.split(" ")

            when (cmd[0]) {
                "move" -> {
                    cleanerState = moveFunc(::transferToCleaner, cmd[1].toInt(), cleanerState)
                }
                "turn" -> {
                    cleanerState = turnFunc(::transferToCleaner, cmd[1].toInt(), cleanerState)
                }
                "set" -> {
                    cleanerState = setStateFunc(::transferToCleaner, cmd[1], cleanerState)
                }
                "start" -> {
                    cleanerState = startFunc(::transferToCleaner, cleanerState)
                }
                "stop" -> {
                    cleanerState = stopFunc(::transferToCleaner, cleanerState)
                }
                else -> continue
            }

            val rez = RobotCommand(state = cleanerState, command = "RESULT")
            outQueue.put(rez)
        }
    }
}

fun commandToQueue(command: RobotCommand): RobotCommand {
    queue.put(command)
    val chunk = outQueue.take()
    return chunk
}
```