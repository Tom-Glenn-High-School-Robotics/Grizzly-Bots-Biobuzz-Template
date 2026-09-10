package org.firstinspires.ftc.teamcode.commands

import com.seattlesolvers.solverslib.command.CommandBase
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem

class DriveDefaultCommand(val driveSubsystem: DriveSubsystem, val gamepad: GamepadEx) : CommandBase() {
    init {
        addRequirements(driveSubsystem)
    }

    override fun execute() {
        driveSubsystem.drive()
    }
}