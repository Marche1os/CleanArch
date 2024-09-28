**Архитектура на монадах состояний.**
К иммутабельности значений, которое было реализовано ранее, добавляется декларативное описание команд робота.
Для такой декларативной ясности потребовалась только одна функция dsl и описание стейта.
Легко композировать управляющие функции (move, turn, ...), не теряя в выразительности и понимании программы. 

Из минусов - повышенные требования к разработчикам, требуется понимание функционального программирования, концепций декларативного программирования и умения создавать выразительный DSL для создания декларативных программ.

```kotlin
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val initialState = RobotState(
        position = RobotState.Position(
            x = 0.0,
            y = 0.0,
        ),
        direction = 0.0,
        mode = RobotState.CleaningDevice.WATER,
        deviceState = RobotState.DeviceState.OFF,
    )

    val program = move(100.0) then
            turn(-90.0) then
            setCleaningDevice(RobotState.CleaningDevice.SOAP) then
            move(50.0) then
            start()

    program.run(initialState)
}

class State<S, out A>(val run: (S) -> Pair<A, S>) {

    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> =
        State { s ->
            val (a, newState) = run(s)
            f(a).run(newState)
        }
}

infix fun <S, A, B> State<S, A>.then(next: State<S, B>): State<S, B> =
    flatMap { next }

fun move(distance: Double): State<RobotState, Result> = State { state ->
    val angleRadians = Math.toRadians(state.direction)
    val deltaX = distance * cos(angleRadians)
    val deltaY = distance * sin(angleRadians)
    val newPosition = RobotState.Position(
        x = state.position.x + deltaX,
        y = state.position.y + deltaY
    )

    val newState = state.copy(position = newPosition)

    newState.logString("POS $newPosition")

    Result.Success to newState
}

fun turn(angle: Double): State<RobotState, Result> = State { state ->
    val newDirection = (state.direction + angle) % 360
    val newState = state.copy(direction = newDirection)

    newState.logString("ANGLE $newDirection")

    Result.Success to newState
}

fun setCleaningDevice(cleaningDevice: RobotState.CleaningDevice): State<RobotState, Result> = State { state ->
    val newState = state.copy(mode = cleaningDevice)

    newState.logString("STATE ${cleaningDevice.value}")

    Result.Success to newState
}

fun start(): State<RobotState, Result> = State { state ->
    val newState = state.copy(deviceState = RobotState.DeviceState.ON)

    newState.logString("START WITH ${state.mode.value}")

    Result.Success to newState
}

fun stop(): State<RobotState, Result> = State { state ->
    val newState = state.copy(deviceState = RobotState.DeviceState.OFF)

    newState.logString("STOP")

    Result.Success to newState
}

data class RobotState(
    val position: Position,
    val direction: Double,
    val mode: CleaningDevice,
    val deviceState: DeviceState,
) {
    data class Position(val x: Double, val y: Double)

    enum class CleaningDevice(val value: String) {
        WATER("water"),
        SOAP("soap"),
        BRUSH("brush"),
    }

    enum class DeviceState {
        ON,
        OFF
    }

    fun logString(text: String) {
        println(text)
    }
}

sealed interface Result {
    data object Success : Result
    data object Error :Result
}
```