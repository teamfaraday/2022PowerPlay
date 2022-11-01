package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ColorDetection extends OpenCvPipeline {

    Telemetry telemetry;
    Mat mat = new Mat();

    public enum Color{

        RED,
        GREEN,
        BLUE

    }

    private Color color;

    public ColorDetection(Telemetry t) {

        this.telemetry = t;

    }

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //yellow, green, purple

        Scalar yellowLowHSV = new Scalar(16.5, 45, 45);
        Scalar yellowHighHSV = new Scalar(36.5, 255, 255);

        Scalar greenLowHSV = new Scalar(60, 100, 100);
        Scalar greenHighHSV = new Scalar(60, 255, 255);

       // Scalar purpleLowHSV = new Scalar();
        //Scalar purpleHighHSV = new Scalar();


        //Core.inRange(mat, lowHSV, highHSV, mat);


        return mat;

    }

    public Color getColor() {


        return color;

    }

}
