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

        boolean groundStageUp = false;
        boolean lowStageUp = false;
        boolean midStageUp = false;
        boolean highStageUp = false;
        boolean slides = false;

        HardwareController hc = new HardwareController(frontLeft, frontRight, backLeft, backRight, viper, left, right);

        waitForStart();

        double minPower = -0.8;
        double maxPower = 0.8;

        while (opModeIsActive()) {


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

                    slides = true;

                    hc.viperRunToPosition(HardwareController.POSITION.BOTTOM, slides);

                    slides = false;
                    lowStageUp = true;

                }

            } else if (!gamepad1.a) {viper.setPower(0);}

            // Mid Level = 23.5 inches
            if (gamepad1.b) {

                if (!midStageUp) {

                    slides = true;

                    hc.viperRunToPosition(HardwareController.POSITION.MIDDLE, slides);

                    slides = false;
                    midStageUp = true;

                }

            } else if (!gamepad1.b) {
                viper.setPower(0);
            }

            // High Level = 33.5 inches
            if (gamepad1.y) {

                if (!highStageUp) {

                    slides = true;

                    hc.viperRunToPosition(HardwareController.POSITION.TOP, slides);

                    slides = false;
                    highStageUp = true;

                }

            } else if (!gamepad1.y) {
                viper.setPower(0);
            }

            if (gamepad1.dpad_up) {

                if (!groundStageUp) {

                    slides = true;

                    hc.viperRunToPosition(HardwareController.POSITION.GROUND, slides);

                    slides = false;
                    groundStageUp = true;
                }
            } else if (!gamepad1.dpad_up) {
                viper.setPower(0);
            }

            if (gamepad1.x) {

                viper.setDirection(DcMotor.Direction.FORWARD);

                slides = true;

                hc.viperReturn(slides);

                slides = false;

                if (groundStageUp) {

                   groundStageUp = false;

                } else if (lowStageUp) {

                    lowStageUp = false;

                } else if (midStageUp) {

                    midStageUp = false;

                } else if (highStageUp) {

                    highStageUp = false;

                }

                viper.setDirection(DcMotor.Direction.REVERSE);

            } else if (!gamepad1.x) {viper.setPower(0);}

            if (gamepad1.left_bumper) {

                hc.openClaw();

            }

            if (gamepad1.right_bumper) {

                hc.closeClaw();

            }
        }
    }

    public void drawerSlideMove(int ticks) {


    }
}