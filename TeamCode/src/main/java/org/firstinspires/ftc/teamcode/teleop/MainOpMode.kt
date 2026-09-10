package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.seattlesolvers.solverslib.command.CommandOpMode
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import org.firstinspires.ftc.teamcode.commands.DriveDefaultCommand
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem

@TeleOp(name = "Main OpMode")
class MainOpMode : CommandOpMode() {

    private lateinit var driveSubsystem: DriveSubsystem
    private lateinit var driveGamepad: GamepadEx

    override fun initialize() {
        driveGamepad = GamepadEx(gamepad1)
        driveSubsystem = DriveSubsystem(hardwareMap, driveGamepad)

        driveSubsystem.defaultCommand = DriveDefaultCommand(driveSubsystem, driveGamepad)
    }
}