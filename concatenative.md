Особенность конкатенативного стиля в довольно строгом контракте, который требуется соблюдать.
Задача верификации правильной последовательности может быть довольно трудной.

Однако API довольно простой и для узкого класса проектов такой стиль был бы подходящим.

```kotlin
fun main() {
    val cleanerApi = CleanerApi()
    val cleanerClient = CleanerClient(cleanerApi)

    val commandStream = "100 move -90 turn soap set start 50 move stop"

    cleanerClient.sendCommandStream(commandStream)
}

class CleanerClient(
    private val api: CleanerApi
) {

    fun sendCommandStream(commandStream: String) {
        println("Клиент: Отправка стрима команд на сервер...")
        api.request(commandStream)
    }
}

class CleanerApi {
    fun request(commandStream: String) {
        val tokens = commandStream.split(" ")
        val stack = Stack<String>()
        
        for (token in tokens) {
            when (token) {
                "move" -> {
                    val value = stack.pop()
                    val distance = value.toDouble()
                    move(distance)
                }

                "turn" -> {
                    val value = stack.pop()
                    val angle = value.toDouble()
                    turn(angle)
                }

                "set" -> {
                    val option = stack.pop()
                    set(option)
                }

                "start" -> {
                    start()
                }

                "stop" -> {
                    stop()
                }

                else -> {
                    stack.push(token)
                }
            }
        }
    }

    private fun move(distance: Double) {
        println("move $distance")
    }

    private fun turn(angle: Double) {
        println("turn $angle")
    }

    private fun set(option: String) {
        println("set $option")
    }

    private fun start() {
        println("start")
    }

    private fun stop() {
        println("stop")
    }
}
```