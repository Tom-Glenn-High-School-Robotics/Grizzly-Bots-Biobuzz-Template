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
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

@Autonomous(name = "Marrow + Pedro + Dashboard Test", group = "Test")
class MarrowTestAuto : OpMode() {
    enum class Alliance { RED, BLUE }

    private lateinit var prompter: Prompter
    private val dashboard = FtcDashboard.getInstance()

    private lateinit var follower: Follower

    // Prompts
    private lateinit var alliancePrompt: OptionPrompt<Alliance>
    private lateinit var delayPrompt: ValuePrompt<Double>

    // State tracking variables
    private var activeAlliance: Alliance = Alliance.RED
    private var pathState = 0
    private var startDelay = 0.0
    private var startTime = 0.0

    override fun init() {
        telemetry = MultipleTelemetry(telemetry, dashboard.telemetry)

        prompter = Prompter(this)

        // 1. Alliance Selection Prompt
        alliancePrompt = OptionPrompt("Select Alliance", Alliance.RED, Alliance.BLUE)
        prompter.prompt("alliance", alliancePrompt)

        // 2. Start Delay Prompt
        delayPrompt = ValuePrompt(
            "Start Delay(s)",
            Double::class.javaObjectType,
            0.0,
            10.0,
            0.0,
            0.5
        )
        prompter.prompt("delay", delayPrompt)

        follower = Constants.createFollower(hardwareMap)
    }

    override fun init_loop() {
        // Runs driver controls (D-Pad / Bumpers) to cycle Marrow prompts
        prompter.run()

        // Telemetry readout during Init to verify selections before hitting Play
        val alliance: Alliance = prompter.getOrDefault("alliance", Alliance.RED)
        val delay: Double = prompter.getOrDefault("delay", 0.0)

        telemetry.addData("Selected Alliance", alliance.name)
        telemetry.addData("Selected Delay (s)", delay)
        telemetry.update()
    }

    override fun start() {
        // Retrieve values directly from prompter
        activeAlliance = prompter.getOrDefault("alliance", Alliance.RED)
        startDelay = prompter.getOrDefault("delay", 0.0)
        startTime = runtime

        telemetry.addData("LOCKED Alliance", activeAlliance.name)
        telemetry.addData("LOCKED Delay", startDelay)
        telemetry.update()

        // Set initial robot pose matching the chosen alliance
        if (activeAlliance == Alliance.RED) {
            follower.setPose(RedPaths.start)
        } else {
            follower.setPose(BluePaths.start)
        }

        pathState = 0
    }

    override fun loop() {
        // 1. Pedro Pathing follower must update every loop cycle
        follower.update()

        // 2. State Machine Path Following
        if (!follower.isBusy) {
            when (pathState) {
                0 -> {
                    // Check if the driver-configured start delay has elapsed
                    if (runtime - startTime >= startDelay) {
                        if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path1())
                        else follower.follow(BluePaths.path1())
                        pathState = 1
                    }
                }
                1 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path2())
                    else follower.follow(BluePaths.path2())
                    pathState = 2
                }
                2 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path3())
                    else follower.follow(BluePaths.path3())
                    pathState = 3
                }
                3 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path4())
                    else follower.follow(BluePaths.path4())
                    pathState = 4
                }
                4 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path5())
                    else follower.follow(BluePaths.path5())
                    pathState = 5
                }
                5 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path6())
                    else follower.follow(BluePaths.path6())
                    pathState = 6
                }
                6 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path7())
                    else follower.follow(BluePaths.path7())
                    pathState = 7
                }
                7 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path8())
                    else follower.follow(BluePaths.path8())
                    pathState = 8
                }
                8 -> {
                    if (activeAlliance == Alliance.RED) follower.follow(RedPaths.path9())
                    else follower.follow(BluePaths.path9())
                    pathState = 9
                }
                9 -> {
                    // All autonomous paths completed
                    pathState = -1
                }
            }
        }

        // 3. FTC Dashboard Telemetry & Field Drawing
        val currentPose = follower.pose()
        val packet = TelemetryPacket()

        packet.put("Path State", pathState)
        packet.put("Alliance", activeAlliance.name)
        packet.put("Robot X", currentPose.x())
        packet.put("Robot Y", currentPose.y())
        packet.put("Robot Heading", Math.toDegrees(currentPose.heading()))

        packet.fieldOverlay()
            .setFill(if (activeAlliance == Alliance.RED) "red" else "blue")
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
