package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareController {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor viper;
    private Servo left;
    private Servo right;

    public enum POSITION {

        BOTTOM,
        MIDDLE,
        TOP,


    }

    public HardwareController(DcMotor l, DcMotor r, DcMotor bl, DcMotor br, DcMotor v, Servo lf, Servo rf) {

        frontLeft = l;
        frontRight = r;
        backLeft = bl;
        backRight = br;
        viper = v;
        left = lf;
        right = rf;

        stopResetEncoders();
        runWithoutEncoders();


    }

    public void setMotorState() {

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        viper.setDirection(DcMotor.Direction.REVERSE);

    }

    public void stopResetEncoders() {

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void runWithoutEncoders() {

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public void runUsingEncoders() {

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void viperRunToPosition(POSITION p, boolean flag) {

        if (p == POSITION.BOTTOM) {

            viper.setTargetPosition(1880);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }

        } else if (p == POSITION.MIDDLE) {

            viper.setTargetPosition(3100);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }

        } else if (p == POSITION.TOP) {

            viper.setTargetPosition(4320);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }

        }


    }

    public void viperReturn(boolean flag) {

        if (flag) {

            viper.setTargetPosition(15);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            viper.setPower(0.6);

            flag = false;

        }
    }

    public void openClaw() {

        left.setPosition(0.23);
        right.setPosition(0.67);

    }

    public void closeClaw() {

        left.setPosition(0.43);
        right.setPosition(0.5);

    }


}
