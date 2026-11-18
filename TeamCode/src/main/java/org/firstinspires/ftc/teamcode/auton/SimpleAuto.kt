package org.firstinspires.ftc.teamcode.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@Autonomous(name = "Go Front Go Back Auto")
class SimpleAuto : LinearOpMode() {

    private lateinit var frontLeft: DcMotor
    private lateinit var frontRight: DcMotor
    private lateinit var backLeft: DcMotor
    private lateinit var backRight: DcMotor

    override fun runOpMode() {
        frontLeft = hardwareMap.get(DcMotor::class.java, "frontLeft")
        frontRight = hardwareMap.get(DcMotor::class.java, "frontRight")
        backLeft = hardwareMap.get(DcMotor::class.java, "backLeft")
        backRight = hardwareMap.get(DcMotor::class.java, "backRight")

        frontRight.direction = DcMotorSimple.Direction.REVERSE
        backRight.direction = DcMotorSimple.Direction.REVERSE

        telemetry.addData("Status", "Ready to run")
        telemetry.update()

        waitForStart()

        if (opModeIsActive()) {
            setMotorPowers(0.3)
            sleep(3000)

            setMotorPowers(-0.3)
            sleep(3000)

            setMotorPowers(0.0)
        }
    }

    private fun setMotorPowers(power: Double) {
        frontLeft.power = power
        frontRight.power = power
        backLeft.power = power
        backRight.power = power
    }
}