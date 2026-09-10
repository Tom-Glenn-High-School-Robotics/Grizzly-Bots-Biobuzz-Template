package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp(name = "Basic TeleOp", group = "Linear OpMode")
class BasicOpMode : LinearOpMode() {

    private lateinit var frontLeft: DcMotor
    private lateinit var frontRight: DcMotor
    private lateinit var backLeft: DcMotor
    private lateinit var backRight: DcMotor

    override fun runOpMode() {
        frontLeft = hardwareMap.get(DcMotor::class.java, "frontLeft")
        frontRight = hardwareMap.get(DcMotor::class.java, "frontRight")
        backLeft = hardwareMap.get(DcMotor::class.java, "backLeft")
        backRight = hardwareMap.get(DcMotor::class.java, "backRight")

        frontRight.direction = frontRight.direction.inverted()
        backRight.direction = backRight.direction.inverted()

        telemetry.addData("Status", "Initialized")
        telemetry.update()

        // Wait for the driver to press PLAY
        waitForStart()

        while (opModeIsActive()) {
            // Read joystick values (Y-axis is normally inverted, so negate it)
            val y = -gamepad1.left_stick_y.toDouble()
            val x = gamepad1.left_stick_x.toDouble()
            val r = gamepad1.right_stick_x.toDouble()

            // Calculate simple arcade/tank power
            var frontLeftPower = y + x + r
            var frontRightPower = y - x - r
            var backLeftPower = y - x + r
            var backRightPower = y + x - r

            // Send power to motors
            frontLeftPower = frontLeftPower.coerceIn(-1.0, 1.0)
            frontRightPower = frontRightPower.coerceIn(-1.0, 1.0)
            backLeftPower = backLeftPower.coerceIn(-1.0, 1.0)
            backRightPower = backRightPower.coerceIn(-1.0, 1.0)

            frontLeft.power = frontLeftPower
            frontRight.power = frontRightPower
            backLeft.power = backLeftPower
            backRight.power = backRightPower

            telemetry.addData("Front Left Pwr", frontLeftPower)
            telemetry.addData("Front Right Pwr", frontRightPower)
            telemetry.addData("Back Left Pwr", backLeftPower)
            telemetry.addData("Back Right Pwr", backRightPower)

            telemetry.update()
        }
    }
}
