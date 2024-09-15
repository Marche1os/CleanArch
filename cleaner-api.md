### API модуль робота. Предоставляется клиентам

```kotlin
interface CleanerApi {

    /**
     * Условная "Авторизация": получение управления.
     * Каждый вызов возвращает новый экземпляр объекта [Cleaner]
     *
     * @return интерфейс управления роботом-чистищиком.
     */
    fun takeControl(): Cleaner

    /**
     * "Отпустить" управление роботом.
     * Заглушка, реальной реализации для учебного задания не предполагается
     */
    fun releaseControl()
}

interface Cleaner {

    fun move(moveMeters: MoveMeters, cleanerState: CleanerState): CleanerState

    fun turn(angle: Angle, cleanerState: CleanerState): CleanerState

    fun set(cleaningDevice: CleaningDevice, cleanerState: CleanerState): CleanerState

    fun start(cleanerState: CleanerState): CleanerState

    fun stop(cleanerState: CleanerState): CleanerState
}
```

Помимо этого в API модуле содержатся типы данных, такие как угол (Angle), позиция робота (Position) и другие.


### IMPL модуль робота

```kotlin
class CleanerApiImpl : CleanerApi {

    override fun takeControl(): Cleaner {
        val cleaner = CleanerImpl(
            movement = RobotMovementImpl()
        )

        return cleaner
    }

    override fun releaseControl() {
        TODO("Not yet implemented")
    }
}

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