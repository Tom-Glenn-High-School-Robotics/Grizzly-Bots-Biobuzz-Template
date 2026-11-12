//package org.firstinspires.ftc.teamcode.subsystems
//
//import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult
//import com.qualcomm.hardware.limelightvision.Limelight3A
//import com.qualcomm.robotcore.hardware.HardwareMap
//import com.seattlesolvers.solverslib.command.SubsystemBase
//import org.firstinspires.ftc.robotcore.external.Telemetry
//
//class HiveVisionSubsystem(
//    hardwareMap: HardwareMap,
//    private val telemetry: Telemetry? = null
//) : SubsystemBase() {
//
//    private val limelight: Limelight3A = hardwareMap.get(Limelight3A::class.java, "limelight")
//
//    var bestTarget: DetectorResult? = null
//        private set
//
//    // Set to "Yellow Pollen", "Red Nectar", "Blue Nectar", or null to target ANY ball
//    var targetFilter: String? = null
//
//    init {
//        limelight.setPollRateHz(100)
//        limelight.pipelineSwitch(0) // Pipeline 0: PollenNectarDetector
//        limelight.start()
//    }
//
//    override fun periodic() {
//        val result = limelight.latestResult
//        if (result != null && result.isValid) {
//            bestTarget = result.detectorResults
//                .filter { targetFilter == null || it.className.equals(targetFilter, ignoreCase = true) }
//                .maxByOrNull { it.targetArea }
//        } else {
//            bestTarget = null
//        }
//
//        telemetry?.let {
//            if (bestTarget != null) {
//                it.addData("Vision Target", bestTarget!!.className)
//                it.addData("Vision Tx (Deg)", "%.2f", bestTarget!!.targetXDegrees)
//                it.addData("Vision Area (%)", "%.2f", bestTarget!!.targetArea)
//            } else {
//                it.addData("Vision Target", "None")
//            }
//        }
//    }
//
//    fun hasTarget(): Boolean = bestTarget != null
//
//    // Horizontal angle offset in degrees (negative = ball is left, positive = ball is right)
//    fun getTx(): Double = bestTarget?.targetXDegrees ?: 0.0
//
//    // Vertical angle offset in degrees
//    fun getTy(): Double = bestTarget?.targetYDegrees ?: 0.0
//}