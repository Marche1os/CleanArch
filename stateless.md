В моем понимании stateless архитектуры одно из моих решений уже соответствует этой архитектуре.
В решении ниже у нас есть набор функций, которые принимают текущее состояние робота и на основе этого состояния возвращают новый стейт робота, не изменяя текущий.
При этом отсутствуют какие-либо поля класса и общие переменные.

Единственное требуется создать в точке входа в приложения этот стейт и в одном месте отслеживать изменения.

```kotlin
class CleanerImpl(
    private val movement: RobotMovement,
) : Cleaner {

    override fun move(moveMeters: MoveMeters, cleanerState: CleanerState): CleanerState {
        val newPosition = movement.move(
            oldPosition = cleanerState.position,
            currentAngle = cleanerState.angle,
            distance = moveMeters,
        )

        println("POS $newPosition")

        return cleanerState.copy(
            position = newPosition,
        )
    }

    override fun turn(angle: Angle, cleanerState: CleanerState): CleanerState {
        val newAngle = cleanerState.angle + angle

        println("ANGLE $newAngle")

        return cleanerState.copy(
            angle = newAngle,
        )
    }

    override fun set(cleaningDevice: CleaningDevice, cleanerState: CleanerState): CleanerState {
        println(cleaningDevice.value)

        return cleanerState.copy(
            cleaningDevice = cleaningDevice,
        )
    }

    override fun start(cleanerState: CleanerState): CleanerState {
        val newState = cleanerState.copy(
            deviceState = DeviceState.ON
        )

        println("START WITH ${cleanerState.cleaningDevice.value}")

        return newState
    }

    override fun stop(cleanerState: CleanerState): CleanerState {
        val newState = cleanerState.copy(
            deviceState = DeviceState.OFF,
        )

        println("STOP")

        return newState
    }
}
```

```kotlin
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