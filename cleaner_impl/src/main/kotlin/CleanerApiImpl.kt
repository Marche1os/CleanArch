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