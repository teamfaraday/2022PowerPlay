package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "TestAutonomous")
public class TestAutonomous extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor viper;


    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        viper = hardwareMap.get(DcMotor.class, "viper");

        Servo left = hardwareMap.get(Servo.class, "left");
        Servo right = hardwareMap.get(Servo.class, "right");

        double power = 0.8;
        double correction = 0;

        PIDManager pid = new PIDManager(0.05,0,0);
        PIDManager pidRotate = new PIDManager(.003, .00003, 0);

        MotorManager mm = new MotorManager(frontLeft, frontRight, backLeft, backRight,viper);
        mm.setMotorState();

        ElapsedTime runtime = new ElapsedTime();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        pid.setSetpoint(-90);
        pid.setOutputRange(0, power);
        pid.setInputRange(-90,90);
        pid.enable();

        //mm.stopResetEncoder();
        //mm.runUsingEncoders();

        waitForStart();

        double minPower = -0.8;
        double maxPower = 0.8;

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        while (opModeIsActive()) {

                correction = pid.performPID(getAngle());
                frontLeft.setPower(0.2);
                frontRight.setPower(0.2);
                backLeft.setPower(0.2);
                backRight.setPower(0.2);

                //frontLeft.setPower(pid.calculate(100, frontLeft.getCurrentPosition()));
                //frontRight.setPower(pid.calculate(100, frontRight.getCurrentPosition()));
                //backLeft.setPower(pid.calculate(100, backLeft.getCurrentPosition()));
                //backRight.setPower(pid.calculate(100, backRight.getCurrentPosition()));


        }

    }

    public void rotate(int degrees, double power) {
        double leftPower, rightPower;
        if (degrees > 0) {

            degrees -= 16;

        } else if (degrees < 0) {

            degrees += 16;

        }

        resetAngle();

        //if the degrees are less than 0, the robot will turn right
        if (degrees < 0) {
            leftPower = power;
            rightPower = -power;
        } else if (degrees > 0)//if greater than 0, turn left
        {
            leftPower = -power;
            rightPower = power;
        } else return;

        //sets power to motors with negative signs properly assigned to make the robot go in the correct direction
        frontLeft.setPower(rightPower);
        backLeft.setPower(rightPower);
        frontRight.setPower(leftPower);
        backRight.setPower(leftPower);

        //Repeatedly check the IMU until the getAngle() function returns the value specified.
        if (degrees < 0) {
            while (opModeIsActive() && getAngle() == 0) {
            }

            while (opModeIsActive() && getAngle() > degrees) {
            }
        } else
            while (opModeIsActive() && getAngle() < degrees) {
            }


        //stop the motors after the angle has been found.

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        //sleep for a bit to make sure the robot doesn't over shoot
        sleep(1000);

        resetAngle();
    }

    public double getAngle() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;

    }

    public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }


}
