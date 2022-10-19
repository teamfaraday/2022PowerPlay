package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "TestAutonomous")
public class TestAutonomous extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");

       /** DcMotor leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        DcMotor rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");
        DcMotor leftClaw = hardwareMap.get(DcMotor.class, "leftClaw");
        DcMotor rightClaw = hardwareMap.get(DcMotor.class, "rightClaw");
**/
        //MotorManager mm = new MotorManager(frontLeft, frontRight, backLeft, backRight, leftIntake, rightIntake, leftClaw, rightClaw);
        //mm.setMotorState();

        BNO055IMU imu;
        Orientation lastAngles = new Orientation();
        double globalAngle;

        ElapsedTime runtime = new ElapsedTime();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //mm.stopResetEncoder();
        //mm.runUsingEncoders();

        waitForStart();

        double minPower = -0.8;
        double maxPower = 0.8;

        while (opModeIsActive()) {

                frontLeft.setPower(0.8);
                frontRight.setPower(0.8);
                backLeft.setPower(0.8);
                backRight.setPower(0.8);
                sleep(1000);


        }

    }
}
