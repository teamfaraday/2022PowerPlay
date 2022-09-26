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

        MotorManager mm = new MotorManager(frontLeft, frontRight, backLeft, backRight);
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

        }


    }
}
