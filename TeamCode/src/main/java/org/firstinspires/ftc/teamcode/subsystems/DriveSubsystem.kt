package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.seattlesolvers.solverslib.command.SubsystemBase
import com.seattlesolvers.solverslib.drivebase.MecanumDrive
import com.seattlesolvers.solverslib.gamepad.GamepadEx
import com.seattlesolvers.solverslib.hardware.motors.MotorEx

class DriveSubsystem(val hardwareMap: HardwareMap, val gamepad: GamepadEx) : SubsystemBase() {
    var frontLeft: MotorEx
    var frontRight: MotorEx
    var backLeft: MotorEx
    var backRight: MotorEx

    var drive: MecanumDrive

    init {
        frontLeft = MotorEx(hardwareMap, "frontLeft")
        frontRight = MotorEx(hardwareMap, "frontRight")
        backLeft = MotorEx(hardwareMap, "backLeft")
        backRight = MotorEx(hardwareMap, "backRight")

        drive = MecanumDrive(frontLeft, frontRight, backLeft, backRight)
    }

    fun drive() {
        drive.driveRobotCentric(-gamepad.leftX, -gamepad.leftY, -gamepad.rightX)
    }
}