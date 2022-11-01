package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ViperTest")
public class ViperTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor viperLeft = hardwareMap.get(DcMotor.class, "viperLeft");
        DcMotor viperRight = hardwareMap.get(DcMotor.class, "viperRight");

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.a) {

                viperLeft.setPower(-0.3);
                viperRight.setPower(-0.3);

            } else if (!gamepad1.a) {

                viperLeft.setPower(0);
                viperRight.setPower(0);

            }

            if (gamepad1.b) {

                viperLeft.setPower(0.3);
                viperRight.setPower(0.3);


            } else if (!gamepad1.b) {

                viperLeft.setPower(0);
                viperRight.setPower(0);

            }
        }

    }
}
