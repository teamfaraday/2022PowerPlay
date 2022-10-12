package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ViperTest")
public class ViperTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor viperLeft = hardwareMap.get(DcMotor.class, "viperLeft");
        DcMotor viperRight = hardwareMap.get(DcMotor.class, "viperRight");

        waitForStart();

        while (opModeIsActive()) {

            viperLeft.setPower(0.8);
            viperRight.setPower(0.8);
            sleep(1000);
        }

    }
}
