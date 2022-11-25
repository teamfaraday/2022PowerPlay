package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name="RedRightAuto",group="Blue")
public class RedRightAuto extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor viper;
    public Servo left;
    public Servo right;

    SleeveDetection sleeveDetection;
    OpenCvWebcam webcam;

    String webcamName = "webcam";

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
        left = hardwareMap.get(Servo.class, "left");
        right = hardwareMap.get(Servo.class, "right");

        HardwareController mm = new HardwareController(frontLeft, frontRight, backLeft, backRight, viper, left, right);
        mm.setMotorState();
        viper.setDirection(DcMotor.Direction.REVERSE);

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        webcam.setPipeline(sleeveDetection);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        while (!isStarted()) {

            //yellow: left, cyan: middle, magenta: right

            telemetry.addData("Where to park: ", sleeveDetection.getPosition());
            telemetry.update();
        }

        waitForStart();

        SleeveDetection.ParkingPosition sd = sleeveDetection.getPosition();

        if (sd == SleeveDetection.ParkingPosition.LEFT) {

            strafeLeft(3, 0.8);
            forward(28, 0.8);
            strafeLeft(38, 0.8);

            //Go Low
            while(opModeIsActive()) {

                viper.setTargetPosition(4120);

                viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                viper.setPower(0.8);

                sleep(2000);

                left.setPosition(0.33);
                right.setPosition(0.57);

                sleep(1000);

                left.setPosition(0.45);
                right.setPosition(0.36);

                break;

            }

            strafeRight(12, 0.8);


        } else if (sd == SleeveDetection.ParkingPosition.CENTER) {

            strafeLeft(3, 0.8);
            forward(28, 0.8);
            strafeLeft(38, 0.8);

            //Go Low
            while(opModeIsActive()) {

                viper.setTargetPosition(4120);

                viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                viper.setPower(0.8);

                sleep(2000);

                left.setPosition(0.23);
                right.setPosition(0.67);

                sleep(1000);

                left.setPosition(0.43);
                right.setPosition(0.46);

                break;

            }

            strafeRight(28, 0.8);

        } else if (sd == SleeveDetection.ParkingPosition.RIGHT) {

            strafeLeft(3, 0.8);
            forward(28, 0.8);
            strafeLeft(38, 0.8);

            //Go Low
            while(opModeIsActive()) {

                viper.setTargetPosition(4120);

                viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                viper.setPower(0.8);

                sleep(2000);

                left.setPosition(0.33);
                right.setPosition(0.57);

                sleep(1000);

                left.setPosition(0.45);
                right.setPosition(0.36);

                break;

            }

            strafeRight(56, 0.8);
        }
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
        if (opModeIsActive()) {
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
                frontLeft.setPower(speed+0.06);
                frontRight.setPower(speed);
                backLeft.setPower(speed);
                backRight.setPower(speed+0.06);

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
        if (opModeIsActive()) {
            // howMuch is in inches. A negative howMuch moves backward.

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
                frontRight.setPower(speed+0.06);
                backLeft.setPower(speed+0.06);
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

    public void moveLow() {

        viper.setTargetPosition(1680);

        viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viper.setPower(0.86);

    }

    public void moveMiddle() {

        viper.setTargetPosition(3000);

        viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viper.setPower(0.86);

    }

    public void moveHigh() {

        viper.setTargetPosition(4120);

        viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viper.setPower(0.86);

    }

    public void moveDown() {

        viper.setDirection(DcMotor.Direction.FORWARD);

        viper.setTargetPosition(15);

        viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viper.setPower(0.86);

        viper.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void release() {

        left.setPosition(0.23);
        right.setPosition(0.67);

    }

    public void close() {

        left.setPosition(0.43);
        right.setPosition(0.46);
    }
}