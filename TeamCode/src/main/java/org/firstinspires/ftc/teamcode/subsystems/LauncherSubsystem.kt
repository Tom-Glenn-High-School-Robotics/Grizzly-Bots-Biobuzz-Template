package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.controller.PIDFController
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.gamepad.GamepadKeys
import com.seattlesolvers.solverslib.hardware.motors.Motor
import com.seattlesolvers.solverslib.hardware.motors.MotorEx
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs

class LauncherSubsystem(
    val hardwareMap: HardwareMap,
    val gamepad: GamepadEx,       // gp 1 controls the speed
    baseTelemetry: Telemetry
) : SubsystemBase() {

    private val launcherMotor: MotorEx = MotorEx(hardwareMap, "launcherMotor")
    val dashboardTelemetry = MultipleTelemetry(baseTelemetry, FtcDashboard.getInstance().telemetry)

    var speed: Double = 0.0
    private var p1PreviousA = false
    private var p1PreviousB = false
    private var p1PreviousX = false // this tracks the x button

    private val pidf = PIDFController(LauncherConstants.kP, LauncherConstants.kI, LauncherConstants.kD, LauncherConstants.kF)

    init {
        launcherMotor.setRunMode(Motor.RunMode.RawPower)
    }

    override fun periodic() {
        val p1CurrentB = gamepad.getButton(GamepadKeys.Button.B)
        val p1CurrentA = gamepad.getButton(GamepadKeys.Button.A)
        val p1CurrentX = gamepad.getButton(GamepadKeys.Button.X)

        // this poo poo changes target speed with a and b, or instantly goes to 2600 with x
        if (p1CurrentA && !p1PreviousA && speed <= 5500) speed += 100
        if (p1CurrentB && !p1PreviousB && speed >= 500) speed -= 100
//        if (p1CurrentX && !p1PreviousX) speed = 2600.0

        p1PreviousA = p1CurrentA
        p1PreviousB = p1CurrentB
        p1PreviousX = p1CurrentX

        val targetTicksPerSecond = speed * 28.0 / 60.0
        val currentTicksPerSecond = launcherMotor.velocity

        // Update coefficients in case they were changed via dashboard
        pidf.setPIDF(LauncherConstants.kP, LauncherConstants.kI, LauncherConstants.kD, LauncherConstants.kF)

        // Calculate power using SolversLib PIDF controller
        var motorPower = pidf.calculate(currentTicksPerSecond, targetTicksPerSecond)

        // Prevent overshooting: clear total error when outside ~200 RPM of target
        if (abs(pidf.positionError) >= (200.0 * 28.0 / 60.0)) {
            pidf.clearTotalError()
        }

        // make sure power is clamped between 0 and 1
        motorPower = motorPower.coerceIn(0.0, 1.0)

        launcherMotor.set(motorPower)

        val actualRpm = currentTicksPerSecond * 60.0 / 28.0

        dashboardTelemetry.addData("Target RPM", speed)
        dashboardTelemetry.addData("Actual RPM", actualRpm)
        dashboardTelemetry.addData("Motor Power", motorPower)
        dashboardTelemetry.update()
    }
}