package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import kotlin.DslMarker;

@Disabled
@Autonomous(name="BlueLeftAutoPark",group="Blue")
public class BlueLeftAutoPark extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor viper;
    public Servo claw;

    private int lfPos;
    private int rfPos;
    private int lbPos;
    private int rbPos;

    static final int TICKS_PER_INCH = 50;
    static final double DISTANCE_RATIO = 0.8535;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        viper = hardwareMap.get(DcMotor.class, "viper");
        claw = hardwareMap.get(Servo.class, "claw");

        //HardwareController mm = new HardwareController(frontLeft, frontRight, backLeft, backRight, viper, left, right);
        //mm.setMotorState();

        BNO055IMU imu;
        Orientation lastAngles = new Orientation();
        double globalAngle;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //imu initialize break
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();
        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Driver ready to start
        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();

        //actual code under
        forward(11,0.7);
    }

    public void forward(int inches, double speed) {

        if(opModeIsActive()) {
            // Determine new target position, and pass to motor controller

            // fetch motor positions
            lfPos = frontLeft.getCurrentPosition();
            rfPos = frontRight.getCurrentPosition();
            lbPos = backLeft.getCurrentPosition();
            rbPos = backRight.getCurrentPosition();

            // calculate new targets
            lfPos += (int) (inches * TICKS_PER_INCH * DISTANCE_RATIO);
            rfPos += (int) (inches * TICKS_PER_INCH * DISTANCE_RATIO);
            lbPos += (int) (inches * TICKS_PER_INCH * DISTANCE_RATIO);
            rbPos += (int) (inches * TICKS_PER_INCH * DISTANCE_RATIO);

            // move robot to new position
            frontLeft.setTargetPosition(lfPos);
            frontRight.setTargetPosition(rfPos);
            backLeft.setTargetPosition(lbPos);
            backRight.setTargetPosition(rbPos);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {

                frontLeft.setPower(Math.abs(speed));
                frontRight.setPower(Math.abs(speed));
                backLeft.setPower(Math.abs(speed));
                backRight.setPower(Math.abs(speed));

            }

            // Stop all motion;
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    private void strafeRight(int inches, double speed) {

        if(opModeIsActive()) {

            // howMuch is in inches. A negative howMuch moves backward.

            // fetch motor positions
            lfPos = frontLeft.getCurrentPosition();
            rfPos = frontRight.getCurrentPosition();
            lbPos = backLeft.getCurrentPosition();
            rbPos = backRight.getCurrentPosition();

            // calculate new targets
            lfPos += (int) (inches * TICKS_PER_INCH);
            rfPos -= (int) (inches * TICKS_PER_INCH);
            lbPos -= (int) (inches * TICKS_PER_INCH);
            rbPos += (int) (inches * TICKS_PER_INCH);

            // move robot to new position
            frontLeft.setTargetPosition(lfPos);
            frontRight.setTargetPosition(rfPos);
            backLeft.setTargetPosition(lbPos);
            backRight.setTargetPosition(rbPos);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {

                runtime.reset();
                frontLeft.setPower(speed);
                frontRight.setPower(speed);
                backLeft.setPower(speed);
                backRight.setPower(speed);

            }

            // Stop all motion;
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


    private void strafeLeft(int inches, double speed) {
        // howMuch is in inches. A negative howMuch moves backward.

        if(opModeIsActive()) {

            // fetch motor positions
            lfPos = frontLeft.getCurrentPosition();
            rfPos = frontRight.getCurrentPosition();
            lbPos = backLeft.getCurrentPosition();
            rbPos = backRight.getCurrentPosition();

            // calculate new targets
            lfPos -= (int) (inches * TICKS_PER_INCH);
            rfPos += (int) (inches * TICKS_PER_INCH);
            lbPos += (int) (inches * TICKS_PER_INCH);
            rbPos -= (int) (inches * TICKS_PER_INCH);

            // move robot to new position
            frontLeft.setTargetPosition(lfPos);
            frontRight.setTargetPosition(rfPos);
            backLeft.setTargetPosition(lbPos);
            backRight.setTargetPosition(rbPos);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {

                runtime.reset();
                frontLeft.setPower(speed);
                frontRight.setPower(speed);
                backLeft.setPower(speed);
                backRight.setPower(speed);

            }

            // Stop all motion;
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}