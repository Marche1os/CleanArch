interface RobotMovement {

    fun move(oldPosition: Position, currentAngle: Angle, distance: MoveMeters): Position
}