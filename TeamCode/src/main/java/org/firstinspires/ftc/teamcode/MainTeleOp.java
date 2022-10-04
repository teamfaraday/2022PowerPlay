package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        DcMotor rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");

        MotorManager mm = new MotorManager(frontLeft, frontRight, backLeft, backRight, leftIntake, rightIntake);
        mm.setMotorState();

        waitForStart();

        double minPower = -0.8;
        double maxPower = 0.8;

        while (opModeIsActive()) {

            /**
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.25;
            double rx = gamepad1.right_stick_x;

            frontLeft.setPower(Range.clip(y + x + rx, minPower, maxPower));
            backLeft.setPower(Range.clip(y - x + rx, minPower, maxPower));
            frontRight.setPower(Range.clip(y - x - rx, minPower, maxPower));
            backRight.setPower(Range.clip(y + x - rx, minPower, maxPower));

             **/

            frontLeft.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));
            backLeft.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));

            frontRight.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));
            backRight.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));

            if(gamepad1.a){

                leftIntake.setPower(0.6);
                rightIntake.setPower(0.6);

            } else if (!gamepad1.a) {

                leftIntake.setPower(0);
                rightIntake.setPower(0);


            }

            if (gamepad1.b){

                leftIntake.setPower(-0.6);
                rightIntake.setPower(-0.6);

            } else if (!gamepad1.b) {

                leftIntake.setPower(0);
                rightIntake.setPower(0);

            }

        }
    }
}
