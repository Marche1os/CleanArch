import types.Angle
import types.CleaningDevice

interface Robot {

    /**
     * Команда "Двигаться вперед на [meters] метров"
     */
    fun move(meters: Int)

    /**
     * Команда "Повернуть на [angle] градусов"
     */
    fun turn(angle: Angle)

    /**
     * Выбрать устройство очистки
     */
    fun set(cleaningDevice: CleaningDevice)

    /**
     * Включить устройство очистки
     */
    fun start()

    /**
     * Выключить устройство очистки
     */
    fun stop()
}