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