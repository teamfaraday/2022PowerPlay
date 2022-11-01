package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor viper = hardwareMap.get(DcMotor.class, "viper");

        Servo left = hardwareMap.get(Servo.class, "left");
        Servo right = hardwareMap.get(Servo.class, "right");


        boolean lowStageUp = false;
        boolean midStageUp = false;
        boolean highStageUp = false;

        boolean clawOpen = true;
        boolean lastPressed = false;

        final int FIRST_LEVEL = 100;
        final int SECOND_LEVEL = 0;
        final int THIRD_LEVEL = 0;

        MotorManager mm = new MotorManager(frontLeft, frontRight, backLeft, backRight, viper);

        mm.setMotorState();

        waitForStart();

        double minPower = -0.8;
        double maxPower = 0.8;

        while (opModeIsActive()) {


            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.25;
            double rx = gamepad1.right_stick_x;

            frontLeft.setPower(Range.clip(y + x + rx, minPower, maxPower));
            backLeft.setPower(Range.clip(y - x + rx, minPower, maxPower));
            frontRight.setPower(Range.clip(y - x - rx, minPower, maxPower));
            backRight.setPower(Range.clip(y + x - rx, minPower, maxPower));

            // Low Level = 13.5 inches
            if (gamepad1.a) {

                if (!lowStageUp) {

                    viper.setTargetPosition(1280);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (viper.isBusy()) {

                        resetRuntime();
                        viper.setPower(0.8);

                        //500 ticks / 12.5 inches
                    }

                    lowStageUp = true;

                }


            } else if (!gamepad1.a) {

                viper.setPower(0);

            }

            // Mid Level = 23.5 inches
            if (gamepad1.b) {

                if (!midStageUp) {

                    viper.setTargetPosition(2500);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (viper.isBusy()) {

                        resetRuntime();
                        viper.setPower(0.4);

                        //500 ticks / 12.5 inches

                        midStageUp = true;
                    }
                }

            } else if (!gamepad1.b) {}

            // High Level = 33.5 inches
            if (gamepad1.y) {

                if (!highStageUp) {

                    viper.setTargetPosition(39 20);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (viper.isBusy()) {

                        resetRuntime();
                        viper.setPower(0.8);

                        //500 ticks / 12.5 inches
                    }

                    highStageUp = true;
                }

            } else if (!gamepad1.y) {}
            if (gamepad1.left_bumper) {

                left.setPosition(0.5);
                right.setPosition(0.4);

            }

            if (gamepad1.right_bumper) {

                left.setPosition(0.7);
                right.setPosition(0.2);

            }


        }
    }
}
