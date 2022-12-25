package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareController {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor viper;
    private Servo claw;

    private int lfPos;
    private int rfPos;
    private int lbPos;
    private int rbPos;

    static final int TICKS_PER_INCH = 50;
    static final double DISTANCE_RATIO = 0.8535;

    public enum POSITION {

        GROUND,
        BOTTOM,
        MIDDLE,
        TOP,


    }

    public HardwareController(DcMotor l, DcMotor r, DcMotor bl, DcMotor br, DcMotor viper, Servo c) {

        frontLeft = l;
        frontRight = r;
        backLeft = bl;
        backRight = br;
        viper = l;
        claw = c;

        lfPos = 0;
        rfPos = 0;
        lbPos = 0;
        rbPos = 0;

        setMotorState();
        stopResetEncoders();
        runWithoutEncoders();


    }

    public void setMotorState() {

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    public void stopResetEncoders() {

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void runWithoutEncoders() {

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public void runUsingEncoders() {

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void viperRunToPosition(POSITION p, boolean flag) {

        if (p == POSITION.GROUND) {

            viper.setTargetPosition(600);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }


        } else if (p == POSITION.BOTTOM) {

            viper.setTargetPosition(1880);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }

        } else if (p == POSITION.MIDDLE) {

            viper.setTargetPosition(3100);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }

        } else if (p == POSITION.TOP) {

            viper.setTargetPosition(4320);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (flag) {

                viper.setPower(0.86);

            }

        }
    }

    public void viperReturn(boolean flag) {

        if (flag) {

            viper.setTargetPosition(15);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            viper.setPower(0.86);

        }
    }


    public void autoForward(int inches, double speed) {

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

    public void autoStrafeRight(int inches, double speed) {

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

            frontLeft.setPower(speed + 0.06);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed + 0.06);

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

    public void autoStrafeLeft(int inches, double speed) {

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

            frontLeft.setPower(speed );
            frontRight.setPower(speed + 0.06);
            backLeft.setPower(speed + 0.06);
            backRight.setPower(speed );

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

    public void autoViperRunToPosition(POSITION p, double speed) {

        if (p == POSITION.GROUND) {

            viper.setTargetPosition(600);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(viper.isBusy()) {

                viper.setPower(0.86);

            }


        } else if (p == POSITION.BOTTOM) {

            viper.setTargetPosition(1880);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (viper.isBusy()) {

                viper.setPower(0.86);

            }

        } else if (p == POSITION.MIDDLE) {

            viper.setTargetPosition(3100);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (viper.isBusy()) {

                viper.setPower(0.86);

            }

        } else if (p == POSITION.TOP) {

            viper.setTargetPosition(4320);

            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (viper.isBusy()) {

                viper.setPower(0.86);

            }

        }

    }

    public void autoViperReturn() {

        viper.setTargetPosition(15);

        viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(viper.isBusy()) {

            viper.setPower(0.86);

        }
    }

}




