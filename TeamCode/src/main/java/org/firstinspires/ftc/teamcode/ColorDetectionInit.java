package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Color Detection")
public class ColorDetectionInit extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        CameraManager cm = new CameraManager(hardwareMap, "webcam", telemetry);

        telemetry.addLine("Image detection finished");
        telemetry.update();

        waitForStart();

        telemetry.addLine("Color: " + cm.getColor());


    }
}
