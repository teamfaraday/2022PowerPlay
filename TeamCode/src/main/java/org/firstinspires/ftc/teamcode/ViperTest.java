package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ViperTest")
public class ViperTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor viper = hardwareMap.get(DcMotor.class, "viper");

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.a) {

                viper.setPower(-0.3);

            } else if (!gamepad1.a) {

                viper.setPower(0);

            }

            if (gamepad1.b) {

                viper.setPower(0.3);


            } else if (!gamepad1.b) {

                viper.setPower(0);

            }
        }

    }
}
