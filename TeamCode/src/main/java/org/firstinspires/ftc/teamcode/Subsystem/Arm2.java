package org.firstinspires.ftc.teamcode.Subsystem;

import android.app.backup.BackupAgent;

import com.SCHSRobotics.HAL9001.system.config.ConfigData;
import com.SCHSRobotics.HAL9001.system.config.ConfigParam;
import com.SCHSRobotics.HAL9001.system.config.TeleopConfig;
import com.SCHSRobotics.HAL9001.system.robot.Robot;
import com.SCHSRobotics.HAL9001.system.robot.SubSystem;
import com.SCHSRobotics.HAL9001.util.control.Button;
import com.SCHSRobotics.HAL9001.util.control.CustomizableGamepad;

import com.SCHSRobotics.HAL9001.util.control.Toggle;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptGamepadRumble;
import org.firstinspires.ftc.teamcode.Baguette;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Arm2 extends SubSystem {
    public CustomizableGamepad gamepad;
    public Baguette robot;

    public static Servo clampServo;
    public static Servo elbowJoint;

    public static final String CLAMP_SERVO_BUTTON = "CLAMP_SERVO_BUTTON";

    public static final String LOWER_ELBOW_SERVO = "LOWER_ELBOW_SERVO";
    public static final String RAISE_ELBOW_SERVO = "RAISE_ELBOW_SERVO";

    public static int elbowState = 0;
    private boolean heldElbowLast = false;

    private Toggle clampToggle = new Toggle(Toggle.ToggleTypes.flipToggle, true);

    //public ConfigData data;
    //private int framesToSkip = 3;

    public Arm2(@NotNull Baguette _robot, String _ClAMP_CONFIG, String _ELBOW_CONFIG) {
        super(_robot);

        robot = _robot;

        clampServo = robot.hardwareMap.servo.get(_ClAMP_CONFIG);
        elbowJoint = robot.hardwareMap.servo.get(_ELBOW_CONFIG);

        gamepad = new CustomizableGamepad(_robot);

        usesConfig = true;
    }

    @Override
    public void init() {
        elbowJoint.setPosition(0.25);
        clampServo.setPosition(1);

    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        clampServo.setPosition(1);
        if (usesConfig && !robot.isAutonomous()) {
            //data = robot.pullNonGamepad(this);
            gamepad = robot.pullControls(this);
            //isSpinMotorButtonHeld = data.getData(SPIN_MOTOR_BUTTON, Boolean.class);
        }
    }

    @Override
    public void handle() {
        robot.telemetry.addData(" " + elbowState, "elbow");

        if ((boolean)gamepad.getInput(LOWER_ELBOW_SERVO) || (boolean)gamepad.getInput(RAISE_ELBOW_SERVO)) {
            if (!heldElbowLast) {
                heldElbowLast = true;

                if ((boolean) gamepad.getInput(LOWER_ELBOW_SERVO) && elbowState != 0) {
                    elbowState--;
                    robot.telemetry.addData("lower", "elbow");
                }
                if ((boolean) gamepad.getInput(RAISE_ELBOW_SERVO) && elbowState != 9) {
                    elbowState++;
                    robot.telemetry.addData("raise", "elbow");
                }
            }
        }
        else {
            heldElbowLast = false;
        }


        if (elbowState == 0) {
            elbowJoint.setPosition(0.2);
        }
        else if (elbowState >= 4) {
            elbowJoint.setPosition((elbowState * (.1/7) + 0.63));
        }
        else {
            elbowJoint.setPosition((elbowState * (0.15/7)) + 0.425);
        }

        robot.telemetry.addData(" "  + elbowJoint.getPosition(), "elbow");
        //robot.telemetry.update();
        clampToggle.updateToggle((boolean)gamepad.getInput(CLAMP_SERVO_BUTTON));
        if (clampToggle.getCurrentState()) {
            clampServo.setPosition(1);
        }
        else {
            clampServo.setPosition(0.5);
        }
    }

    @Override
    public void stop() {

    }

    @TeleopConfig
    public static ConfigParam[] teleopConfig() {
        return new ConfigParam[] {
                new ConfigParam(LOWER_ELBOW_SERVO, Button.BooleanInputs.dpad_left, 2),
                new ConfigParam(RAISE_ELBOW_SERVO, Button.BooleanInputs.dpad_right, 2),
                new ConfigParam(CLAMP_SERVO_BUTTON, Button.BooleanInputs.b, 2)
        };
    }

    public void setArm() {
        int level;
        if (Baguette.realPosition.getLevel() == Baguette.TSEPosition.LEFT.getLevel()) {
            level = 1;
        }
        else if (Baguette.realPosition.getLevel() == Baguette.TSEPosition.CENTER.getLevel()) {
            level = 2;
        }
        else {
            level = 3;
        }
        switch (level) {
            default:
                robot.telemetry.addData("arm case:", "default");
                break;
            case 0:
                elbowJoint.setPosition(0.2); waitTime(1000);
                break;
            case 3:
                robot.telemetry.addData("level: ","3");
                robot.telemetry.update();
                elbowJoint.setPosition(0.55); waitTime(1000);
                break;
            case 2:
                robot.telemetry.addData("level: ","2");
                robot.telemetry.update();
                elbowJoint.setPosition(0.63); waitTime(1000);
                break;
            case 1:
                robot.telemetry.addData("level: ","1");
                robot.telemetry.update();
                elbowJoint.setPosition(0.7); waitTime(1000);
                break;
        }
    }
    public void dropArm() {
        clampServo.setPosition(0.5); waitTime(1000);
    }

    public void resetArm() {
        clampServo.setPosition(1); elbowJoint.setPosition(0.3);
    }
}