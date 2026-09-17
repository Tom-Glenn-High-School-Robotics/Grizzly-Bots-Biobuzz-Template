package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.gamepad.GamepadKeys
import com.seattlesolvers.solverslib.hardware.motors.Motor
import com.seattlesolvers.solverslib.hardware.motors.MotorEx
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

class LauncherSubsystem(
    val hardwareMap: HardwareMap,
    val gamepad: GamepadEx,
    val telemetry: Telemetry
) : SubsystemBase() {

    val launcherMotor: MotorEx
    var speed: Double = 0.0

    private var previousA = false
    private var previousB = false

    init {
        launcherMotor = MotorEx(hardwareMap, "launcherMotor", 28.0, 6000.0)

        launcherMotor.setRunMode(Motor.RunMode.VelocityControl)
    }

    override fun periodic() {

        val currentB = gamepad.getButton(GamepadKeys.Button.B)
        val currentA = gamepad.getButton(GamepadKeys.Button.A)

        if (currentA && !previousA && speed <= 5500) {
            speed += 500
        }

        if (currentB && !previousB && speed >= 500) {
            speed -= 500
        }

        previousA = currentA
        previousB = currentB
        val ticksPerSecond = speed * 28.0 / 60.0

        launcherMotor.setVelocity(
            ticksPerSecond
        )

        telemetry.addData("A pressed", currentA)
        telemetry.addData("B pressed", currentB)
        telemetry.addData("Target RPM", speed)
        telemetry.addData("Actual velocity", launcherMotor.rate * 60/28)
        telemetry.update()

    }
}
