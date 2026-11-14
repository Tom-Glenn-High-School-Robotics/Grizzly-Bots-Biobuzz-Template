package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.command.button.GamepadButton
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.gamepad.GamepadKeys
import com.seattlesolvers.solverslib.hardware.servos.ServoEx
import org.firstinspires.ftc.robotcore.external.Telemetry

class IntakeRampSubsystem(
    val hardwareMap: HardwareMap,
    val gamepadEx: GamepadEx,
    val telemetry: Telemetry
) : SubsystemBase() {
    var servo: ServoEx
    var button: GamepadButton
    var open: Boolean = false

    private val ANGLE_STRAIGHT_UP = 90.0
    private val ANGLE_60_CCW = ANGLE_STRAIGHT_UP + 45.0

    init {
        servo = ServoEx(hardwareMap, "flowerRamp", 180.0)

        servo.set(ANGLE_STRAIGHT_UP)

        button = GamepadButton(gamepadEx, GamepadKeys.Button.A)

        button.whenPressed(Runnable {
            open = !open
            if (open) {
                servo.set(ANGLE_60_CCW)
            } else {
                servo.set(ANGLE_STRAIGHT_UP)
            }
        })
    }

    override fun periodic() {
        telemetry.addData("Intake Ramp Open", open)
        telemetry.addData("Intake Ramp Servo Pos", servo.get())
    }
}