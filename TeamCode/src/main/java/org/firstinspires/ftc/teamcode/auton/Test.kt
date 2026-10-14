package org.firstinspires.ftc.teamcode.auton

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import com.skeletonarmy.marrow.prompts.ValuePrompt
import com.pedropathing.follower.Follower
import com.pedropathing.api.PoseFactory
import com.pedropathing.api.Paths
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

@Autonomous(name = "Marrow + Pedro + Dashboard Test", group = "Test")
class MarrowTestAuto : OpMode() {
    enum class Alliance { RED, BLUE }

    private lateinit var prompter: Prompter
    private val dashboard = FtcDashboard.getInstance()

    private lateinit var follower: Follower
    private val p = PoseFactory.degrees()

    override fun init() {
        telemetry = MultipleTelemetry(telemetry, dashboard.telemetry)
//marrow
        prompter = Prompter(this)
        prompter.prompt("alliance", OptionPrompt("Select Alliance", Alliance.RED, Alliance.BLUE))
            .prompt("delay", ValuePrompt("Start Delay (s)", Double::class.java, 0.0, 10.0, 0.0, 1.0))

        follower = Constants.createFollower(hardwareMap)

        // Set starting pose (X: 0, Y: 0, Heading: 0 degrees)
        val startPose = p.of(0.0, 0.0, 0.0)
        follower.setPose(startPose)
    }

    override fun init_loop() {
        //marrow runner
        prompter.run()
    }

    override fun start() {
        val alliance: Alliance = prompter.getOrDefault("alliance", Alliance.RED)
        val delay: Double = prompter.getOrDefault("delay", 0.0)

        telemetry.addData("Selected Alliance", alliance.name)
        telemetry.addData("Start Delay", delay)
        telemetry.update()

        val driveForward = Paths.line(p.of(0.0, 0.0, 0.0), p.of(30.0, 0.0, 0.0))
        follower.follow(driveForward)
    }

    override fun loop() {
        follower.update()
        val currentPose = follower.pose()

        val packet = TelemetryPacket()
        packet.put("Robot X", currentPose.x())
        packet.put("Robot Y", currentPose.y())
        packet.put("Robot Heading", Math.toDegrees(currentPose.heading()))

        packet.fieldOverlay()
            .setFill("blue")
            .fillCircle(currentPose.x(), currentPose.y(), 9.0)
            .setStroke("white")
            .strokeLine(
                currentPose.x(),
                currentPose.y(),
                currentPose.x() + (Math.cos(currentPose.heading()) * 12.0),
                currentPose.y() + (Math.sin(currentPose.heading()) * 12.0)
            )

        dashboard.sendTelemetryPacket(packet)
    }
}
