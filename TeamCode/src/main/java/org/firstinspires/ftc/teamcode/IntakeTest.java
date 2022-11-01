package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "IntakeTest")
public class IntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        DcMotor rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");

        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftIntake.setDirection(DcMotor.Direction.REVERSE);

        double minPower = -.8;
        double maxPower = .8;

        waitForStart();

        while(opModeIsActive()) {

            if (gamepad1.y) {

                leftIntake.setPower(0.8);
                rightIntake.setPower(0.8);

            } else if (!gamepad1.y) {

                leftIntake.setPower(0);
                rightIntake.setPower(0);

            }


        }


    }
}
