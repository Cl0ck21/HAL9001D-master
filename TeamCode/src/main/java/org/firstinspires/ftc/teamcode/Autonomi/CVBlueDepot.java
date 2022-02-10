package org.firstinspires.ftc.teamcode.Autonomi;

import com.SCHSRobotics.HAL9001.system.robot.BaseAutonomous;
import com.SCHSRobotics.HAL9001.system.robot.MainRobot;
import com.SCHSRobotics.HAL9001.system.robot.roadrunner_util.HALTrajectory;
import com.SCHSRobotics.HAL9001.util.math.geometry.Point2D;
import com.SCHSRobotics.HAL9001.util.math.units.HALAngleUnit;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Baguette;

@Autonomous(name = "CVBlueDepot", group = "compCV")
public class CVBlueDepot extends BaseAutonomous {
    public @MainRobot
    Baguette robot;

    private void drivePower (double pow, int angleDegrees) {
        //write code that uses the imu to see how far it goes, and add that to the parameters so its based off distance
        //robot.mDrive.movePower(new Vector2D(pow, angleDegrees, HALAngleUnit.DEGREES));
    }

    private void turnAngle (int angleDegrees, int toleranceDegrees) {
        robot.mDrive.turnPID(angleDegrees, HALAngleUnit.DEGREES, toleranceDegrees, HALAngleUnit.DEGREES );
    }

    @Override
    public void main() {
        waitTime(1000);
        robot.vision.camera.closeCameraDevice();

        if (Baguette.realPosition.getLevel() == 1) Baguette.realPosition = Baguette.TSEPosition.CENTER;

        else if (Baguette.realPosition.getLevel() == 2) Baguette.realPosition = Baguette.TSEPosition.RIGHT;

        else Baguette.realPosition = Baguette.TSEPosition.LEFT;
        //robot.mDrive.reverseMotor("f_l_m");
        //robot.mDrive.reverseMotor("f_r_m");
        //robot.mDrive.reverseMotor("b_l_m");
        //robot.mDrive.reverseMotor("b_r_m");
        //robot.mDrive.setAllMotorZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.FLOAT);

        /*HALTrajectory returnRoute = new HALTrajectory(robot.mDrive.trajectoryBuilder(forwardRoute.end(), 0).
                lineTo(new Point2D(0,0)).
                build().
                toRoadrunner(),
                CoordinateMode.HAL);
*/
        //robot.mDrive.followTrajectory(forwardRoute);
        //waitTime(1000);
        //robot.mDrive.turnPID(PI);
        // waitTime(1000);
        //robot.mDrive.turnPID(PI);
        //waitTime(1000);
        //robot.mDrive.turnPID(PI);
        //waitTime(1000);
        //robot.mDrive.followTrajectory(returnRoute);

        /*HALTrajectory rightMarker = new HALTrajectory(robot.mDrive.trajectoryBuilder(returnRoute.end(), 0).

                lineTo(new Point2D(-30,48)).
                build().
                toRoadrunner(),
                CoordinateMode.HAL);*/

        //robot.mDrive.followTrajectory(forwardRoute);
        //robot.mDrive.followTrajectory(returnRoute);
        //robot.mDrive.followTrajectory(rightMarker);



        //scan
        HALTrajectory setForPlace = robot.mDrive.trajectoryBuilder(new Pose2d())
                .splineToConstantHeading(new Point2D(33, 10), 0)
                .build();

        HALTrajectory forward = robot.mDrive.trajectoryBuilder(new Pose2d())
                .splineToConstantHeading(new Point2D(0, 10), 0)
                .build();

        HALTrajectory alignForPark = robot.mDrive.trajectoryBuilder(new Pose2d())
                .splineToConstantHeading(new Point2D(0, -9), 0)
                .build();

        HALTrajectory park = robot.mDrive.trajectoryBuilder(new Pose2d())
                .lineTo(new Point2D(0, 55))
                .build();

        robot.telemetry.addData("vision: ", Baguette.realPosition.getLevel());
        robot.telemetry.update();
        robot.mDrive.followTrajectory(setForPlace);
        waitTime(500);
        // robot.mDrive.turnPID(Math.PI);

        robot.arm2.setArm(); //correat level
        waitTime(500);
        robot.mDrive.followTrajectory(forward);
        waitTime(500);

        robot.arm2.dropArm();
        waitTime(500);

        //robot.mDrive.turnPID(Math.PI);
        robot.mDrive.followTrajectory(alignForPark);
        robot.arm2.resetArm();
        robot.telemetry.addData(robot.mDrive.getPoseEstimate().toString(), "imu");
        robot.telemetry.update();
        robot.mDrive.turnTime(0.5, 550);
        robot.telemetry.addData(robot.mDrive.getPoseEstimate().toString(), "imu");
        robot.telemetry.update();

        robot.intake.setPower();
        robot.mDrive.followTrajectory(park);

        /*robot.mDrive.turnPower(0.4);
        waitTime(1500);
        robot.mDrive.turnPower(0);
        //robot.mDrive.turnPID(180, HALAngleUnit.DEGREES);*/

        /*robot.mDrive.moveSimple(new Vector2D(0, -Converter.inchToEncoder(1)), 0.4);
        robot.intake.dropMarker("color");
        robot.mDrive.moveSimple(new Vector2D(0, Converter.inchToEncoder(4)), 0.4);
        robot.mDrive.turnPower(0.4);
        waitTime(700);
        robot.mDrive.turnPower(0);

        robot.mDrive.moveSimple(new Vector2D(0, Converter.inchToEncoder(8)), 0.4);
        //robot.mDrive.turnPID(0, HALAngleUnit.DEGREES);
        robot.mDrive.turnPower(0.4);
        waitTime(880);
        robot.mDrive.turnPower(0);
        robot.mDrive.moveSimple(new Vector2D(0, Converter.inchToEncoder(5)), 0.4);
        robot.mDrive.turnPower(-0.4);
        waitTime(800);
        robot.mDrive.turnPower(0);
        robot.mDrive.movePower(0, 0.7);
        waitTime(2000);*/

    }
}
