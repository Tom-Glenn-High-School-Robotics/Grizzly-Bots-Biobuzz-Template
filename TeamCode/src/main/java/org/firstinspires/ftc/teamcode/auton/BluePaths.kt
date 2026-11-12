package org.firstinspires.ftc.teamcode.auton

import com.pedropathing.api.Paths.*
import com.pedropathing.api.PoseFactory
import com.pedropathing.paths.Path

object BluePaths {

    private val poseFactory = PoseFactory.degrees().mirrorX(70.75)

    val start = poseFactory.of(55.3075, 8.4617, 90.0)
    private val path1 = poseFactory.of(48.3825, 8.531, 180.0)
    private val point2 = poseFactory.of(7.3679, 8.4364, 180.0)
    private val point3 = poseFactory.of(56.9192, 103.5449, 270.0)
    private val point3Control1 = poseFactory.of(31.1436, 95.3124, 0.0)
    private val point3Control2 = poseFactory.of(41.5334, 124.1672, 0.0)
    private val point4 = poseFactory.of(47.686, 122.1656, 90.0)
    private val point5 = poseFactory.of(47.221, 131.6362, 90.0)
    private val point6 = poseFactory.of(57.0685, 102.2447, 270.0)
    private val point6Control1 = poseFactory.of(40.2936, 110.783, 0.0)
    private val point7 = poseFactory.of(24.4527, 46.7692, 180.0)
    private val point7Control1 = poseFactory.of(19.9127, 106.8646, 0.0)
    private val point8 = poseFactory.of(11.5897, 46.8361, 180.0)
    private val point9 = poseFactory.of(58.916, 14.5971, 90.0)
    private val point9Control1 = poseFactory.of(31.845, 17.137, 0.0)

    fun path1(): Path = line(start, path1).linear(start, path1)

    fun path2(): Path = line(path1, point2).linear(path1, point2)

    fun path3(): Path = curve(point2, point3Control1, point3Control2, point3).linear(point2, point3)

    fun path4(): Path = line(point3, point4).linear(point3, point4)

    fun path5(): Path = line(point4, point5).linear(point4, point5)

    fun path6(): Path = curve(point5, point6Control1, point6).linear(point5, point6)

    fun path7(): Path = curve(point6, point7Control1, point7).linear(point6, point7)

    fun path8(): Path = line(point7, point8).linear(point7, point8)

    fun path9(): Path = curve(point8, point9Control1, point9).linear(point8, point9)

}