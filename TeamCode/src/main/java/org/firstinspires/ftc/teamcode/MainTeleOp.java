package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends OpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    //public DcMotor viper;
    public Servo claw;

    public enum LiftState {
        LIFT_START,
        LIFT_DROP,
        LIFT_CLOSED,
        LIFT_RETRACT
    }

    ;

    // The liftState variable is declared out here
    // so its value persists between loop() calls
    LiftState liftState = LiftState.LIFT_START;

    ElapsedTime et = new ElapsedTime();


    @Override
    public void init() {

        et.reset();

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        //viper = hardwareMap.get(DcMotor.class, "viper");
        claw = hardwareMap.get(Servo.class, "claw");

        //HardwareController hc = new HardwareController(frontLeft, frontRight, backLeft, backRight, viper, claw);
        //viper.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

    }


    @Override
    public void loop() {

        /**
        switch (liftState) {

            case LIFT_START:

                if (gamepad1.a) {

                    viper.setTargetPosition(1680);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.86);


                }

                if (gamepad1.b) {

                    viper.setTargetPosition(3000);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.86);


                }

                if (gamepad1.y) {

                    viper.setTargetPosition(4120);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.86);


                }


                liftState = LiftState.LIFT_DROP;
                break;

            case LIFT_DROP:

                if (gamepad1.left_bumper) {

                    claw.setPosition(0);

                }

                et.reset();
                liftState = LiftState.LIFT_CLOSED;
                break;

            case LIFT_CLOSED:

                if (gamepad1.right_bumper) {

                    claw.setPosition(0.3);

                }

                et.reset();
                liftState = LiftState.LIFT_RETRACT;
                break;

            case LIFT_RETRACT:

                if (gamepad1.x) {

                    viper.setDirection(DcMotor.Direction.FORWARD);

                    viper.setTargetPosition(40);

                    viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    viper.setPower(0.86);

                    viper.setDirection(DcMotor.Direction.REVERSE);


                }

                liftState = LiftState.LIFT_START;
                break;

            default:

                liftState = LiftState.LIFT_START;

        }

        **/

        double minPower = -0.8;
        double maxPower = 0.8;

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.25;
        double rx = gamepad1.right_stick_x * 0.5;

        frontLeft.setPower(Range.clip(y +x +rx,minPower,maxPower));
        backLeft.setPower(Range.clip(y -x +rx,minPower,maxPower));
        frontRight.setPower(Range.clip(y -x -rx,minPower,maxPower));
        backRight.setPower(Range.clip(y +x -rx,minPower,maxPower));


    }
}