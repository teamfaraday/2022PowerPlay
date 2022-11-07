package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TestTeleOp")
public class TestTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor viper = hardwareMap.get(DcMotor.class, "viper");

        Servo left = hardwareMap.get(Servo.class, "left");
        Servo right = hardwareMap.get(Servo.class, "right");

        viper.setDirection(DcMotor.Direction.REVERSE);

        boolean groundStageUp = false;
        boolean lowStageUp = false;
        boolean midStageUp = false;
        boolean highStageUp = false;

        boolean clawOpen = true;
        boolean lastPressed = false;

        final int FIRST_LEVEL = 100;
        final int SECOND_LEVEL = 0;
        final int THIRD_LEVEL = 0;

        waitForStart();

        double minPower = -0.8;
        double maxPower = 0.8;

        while (opModeIsActive()) {

            boolean slide = false;

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.25;
            double rx = gamepad1.right_stick_x * 0.5;

            frontLeft.setPower(Range.clip(y + x + rx, minPower, maxPower));
            backLeft.setPower(Range.clip(y - x + rx, minPower, maxPower));
            frontRight.setPower(Range.clip(y - x - rx, minPower, maxPower));
            backRight.setPower(Range.clip(y + x - rx, minPower, maxPower));

            // Low Level = 13.5 inches
            if (gamepad1.a) {

                if (!lowStageUp) {

                    slide = !slide;

                    viper.setTargetPosition(1880);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    if(slide == true)  {
                        resetRuntime();
                        viper.setPower(0.86);
                    }

                    lowStageUp = true;

                }


            } else if (!gamepad1.a) {

            }

            // Mid Level = 23.5 inches
            if (gamepad1.b) {

                slide = !slide;

                if (!midStageUp) {

                    viper.setTargetPosition(3100);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    if(slide == true) {

                        viper.setPower(0.86);

                    }

                    midStageUp = true;

                }

            } else if (!gamepad1.b) {
            }

            // High Level = 33.5 inches
            if (gamepad1.y) {

                slide = !slide;

                if (!highStageUp) {

                    viper.setTargetPosition(4320);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    if(slide == true) {

                        viper.setPower(0.86);

                    }

                    highStageUp = true;
                }

            } else if (!gamepad1.y) {
            }

            if(gamepad1.dpad_up) {

                if (!groundStageUp) {

                    viper.setTargetPosition(600);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.86);

                    groundStageUp = true;
                }
            }
            else if(!gamepad1.dpad_up) {
            }

            if (gamepad1.x) {

                viper.setDirection(DcMotor.Direction.FORWARD);

                if(groundStageUp) {

                    viper.setTargetPosition(15);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.6);

                    groundStageUp = false;
                }

                else if (lowStageUp) {

                    viper.setTargetPosition(15);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.6);

                    lowStageUp = false;

                } else if (midStageUp) {

                    viper.setTargetPosition(15);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.6);

                    midStageUp = false;

                } else if (highStageUp) {

                    viper.setTargetPosition(15);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.6);

                    highStageUp = false;

                }

                viper.setDirection(DcMotor.Direction.REVERSE);

            } else if (!gamepad1.x) {
                viper.setPower(0.02);
            }

            if (gamepad1.left_bumper) {
                //open

                left.setPosition(0.23);
                right.setPosition(0.67);

            }

            if (gamepad1.right_bumper) {
                //close

                left.setPosition(0.43);
                right.setPosition(0.5);

            }


        }
    }

    public void drawerSlideMove(int ticks) {



    }
}