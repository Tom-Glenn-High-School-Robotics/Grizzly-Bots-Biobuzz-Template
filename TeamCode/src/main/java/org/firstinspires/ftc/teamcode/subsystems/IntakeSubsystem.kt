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

class IntakeSubsystem(
    val hardwareMap: HardwareMap,
    val gamepad: GamepadEx,       // gp 1 controls the speed
    baseTelemetry: Telemetry
) : SubsystemBase() {

    private val intakeMotor: MotorEx = MotorEx(hardwareMap, "intakeMotor")

    var speed: Double = 0.0
    private var p1PreviousA = false
    private var p1PreviousB = false


    init {
        intakeMotor.setRunMode(Motor.RunMode.VelocityControl)

        intakeMotor.inverted = true
    }

    override fun periodic() {
        val p1CurrentB = gamepad.getButton(GamepadKeys.Button.Y)
        val p1CurrentA = gamepad.getButton(GamepadKeys.Button.X)

        // this poo poo changes target speed with a and b, or instantly goes to 2600 with x
        if (p1CurrentA && !p1PreviousA && speed <= 250) speed += 50
        if (p1CurrentB && !p1PreviousB && speed >= 50) speed -= 50

        p1PreviousA = p1CurrentA
        p1PreviousB = p1CurrentB

        val targetTicksPerSecond = speed * 537.7 / 60.0
        val currentTicksPerSecond = intakeMotor.velocity


        intakeMotor.setVelocity(targetTicksPerSecond)

        val actualRpm = currentTicksPerSecond * 60.0 / 28.0
    }
}