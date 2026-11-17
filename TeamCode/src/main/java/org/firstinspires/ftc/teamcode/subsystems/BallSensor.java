package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class BallSensor extends SubsystemBase {
    private final NormalizedColorSensor colorSensor;
    private final Telemetry telemetry;

    public BallSensor(HardwareMap hMap, Telemetry telemetry){
        colorSensor = hMap.get(NormalizedColorSensor.class, "colorSensor");
        colorSensor.setGain(2);
        this.telemetry = telemetry;



    }
    public double getDistance(){
        return ((DistanceSensor)colorSensor).getDistance(DistanceUnit.CM);
    }

    public double getHue(){
        float[] hsv = new float[3];
        Color.colorToHSV(colorSensor.getNormalizedColors().toColor(), hsv);
        return hsv[0];
    }

    public double getSaturation(){
        float[] hsv = new float[3];
        Color.colorToHSV(colorSensor.getNormalizedColors().toColor(), hsv);
        return hsv[1];
    }

    public double getBrightness(){
        float[] hsv = new float[3];
        Color.colorToHSV(colorSensor.getNormalizedColors().toColor(), hsv);
        return hsv[2];
    }

    @Override
    public void periodic(){
        telemetry.addData("Hue", getHue());
        telemetry.addData("Saturation", getSaturation());
        telemetry.addData("Brightness", getBrightness());
        telemetry.addData("Distance", getDistance());
    }
}
