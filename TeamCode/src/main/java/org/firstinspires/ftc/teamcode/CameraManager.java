package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class CameraManager {

    private Telemetry telemetry;
    private OpenCvWebcam webcam;
    private ColorDetection pipeline;

    public CameraManager(HardwareMap hm, String cameraName, Telemetry telemetry) {

        this.telemetry = telemetry;

        int cameraId = hm.appContext.getResources().getIdentifier("cameraId", "id", hm.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hm.get(WebcamName.class, cameraName), cameraId);
        pipeline = new ColorDetection(telemetry);
        webcam.setPipeline(pipeline);
    }

    public void init() {
        openCamera();
    }

    public void openCamera() {

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {

                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);

            }

            @Override
            public void onError(int errorCode) {

                Log.e("CAMERA DEVICE", "Camera could not be opened. ");

            }
        });

    }

    public ColorDetection.Color getColor() {

        return pipeline.getColor();

    }

    public void stopCamera() {

        webcam.stopStreaming();

    }


}
