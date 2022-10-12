package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDManager {

    private double integralSum;
    private double Kp;
    private double Ki;
    private double Kd;
    private double lastError;
    private ElapsedTime et;


    public PIDManager() {

        this.integralSum = 0;
        this.Kp = 0;
        this.Ki = 0;
        this.Kd = 0;
        this.lastError = 0;
        this.et = new ElapsedTime();


    }

    public double calculate(double reference, double state) {

        double error = reference - state;
        integralSum += error * et.seconds();
        double derivative = (error - lastError) / et.seconds();
        lastError = error;

        et.reset();

        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki);
        return output;

    }
}
