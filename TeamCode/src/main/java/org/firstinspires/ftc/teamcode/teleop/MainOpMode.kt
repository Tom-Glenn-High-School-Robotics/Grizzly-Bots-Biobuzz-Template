package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.seattlesolvers.solverslib.command.CommandOpMode
import com.seattlesolvers.solverslib.command.button.GamepadButton
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import org.firstinspires.ftc.teamcode.commands.DriveDefaultCommand
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem
import org.firstinspires.ftc.teamcode.subsystems.IntakeRampSubsystem
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem

@TeleOp(name = "Main OpMode")
class MainOpMode : CommandOpMode() {

    private lateinit var driveSubsystem: DriveSubsystem
    private lateinit var launcherSubsystem: LauncherSubsystem
    private lateinit var intakeRampSubsystem: IntakeRampSubsystem
    private lateinit var driveGamepad: GamepadEx
    private lateinit var launchGamepad: GamepadEx
    private lateinit var intakeSubsystem: IntakeSubsystem

    override fun initialize() {
        driveGamepad = GamepadEx(gamepad1)
        launchGamepad = GamepadEx(gamepad2)
        driveSubsystem = DriveSubsystem(hardwareMap, driveGamepad, telemetry)
        intakeRampSubsystem = IntakeRampSubsystem(hardwareMap, driveGamepad, telemetry)

        launcherSubsystem = LauncherSubsystem(hardwareMap, launchGamepad, telemetry)
        intakeSubsystem = IntakeSubsystem(hardwareMap, launchGamepad, telemetry)

        driveSubsystem.defaultCommand = DriveDefaultCommand(driveSubsystem, driveGamepad)
    }
}