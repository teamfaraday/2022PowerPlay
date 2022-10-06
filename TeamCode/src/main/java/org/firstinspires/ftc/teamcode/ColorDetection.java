package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
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
        return mat;
    }

    public Color getColor() {

        return color;

    }

}
