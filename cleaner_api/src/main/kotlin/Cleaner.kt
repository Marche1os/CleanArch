interface Cleaner {

    fun move(moveMeters: MoveMeters, cleanerState: CleanerState): CleanerState

    fun turn(angle: Angle, cleanerState: CleanerState): CleanerState

    fun set(cleaningDevice: CleaningDevice, cleanerState: CleanerState): CleanerState

    fun start(cleanerState: CleanerState): CleanerState

    fun stop(cleanerState: CleanerState): CleanerState
}