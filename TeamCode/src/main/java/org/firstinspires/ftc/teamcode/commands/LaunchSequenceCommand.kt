package org.firstinspires.ftc.teamcode.commands

import com.seattlesolvers.solverslib.command.CommandBase
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem

class LaunchSequenceCommand(
    private val launcherSubsystem: LauncherSubsystem,
    private val intakeSubsystem: IntakeSubsystem
) : CommandBase() {

    private val timer = ElapsedTime()
    private var isAtSpeed = false

    init {
        // Require both subsystems so driving isn't interrupted,
        // but manual intake/launcher commands are paused
        addRequirements(launcherSubsystem, intakeSubsystem)
    }

    override fun initialize() {
        timer.reset()
        isAtSpeed = false

        // Command the subsystem to start spinning up the motor
        launcherSubsystem.speed = 2600.0
    }

    override fun execute() {
        if (!isAtSpeed) {
            // Wait until the launcher hits the target RPM (using 2550 as a slight buffer)
            if (launcherSubsystem.getCurrentRpm() >= 2550.0) {
                isAtSpeed = true
                timer.reset() // Start the 4000ms timer the moment we reach speed
            }
        } else {
            // We are at speed. Turn on the intake to feed balls into the launcher.
            // Adjust this speed value to whatever successfully feeds your rings/balls
            intakeSubsystem.speed = 250.0
        }
    }

    override fun isFinished(): Boolean {
        // The command completes only after the launcher is at speed AND 4000ms have passed
        return isAtSpeed && timer.milliseconds() > 4000.0
    }

    override fun end(interrupted: Boolean) {
        // Shut everything off when the 4 seconds are up or if the driver cancels it
        launcherSubsystem.speed = 0.0
        intakeSubsystem.speed = 0.0
    }
}