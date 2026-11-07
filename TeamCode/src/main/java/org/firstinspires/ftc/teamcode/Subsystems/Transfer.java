package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer extends SubsystemBase {
    private final Motor transferMotor;
    private final ServoEx switchServo;
    private final Telemetry telemetry;

    // max offset angle for switch servo
    private static final double MAX_ANGLE = 14.75;

    // servo switch angle is 15.223947696 degrees
    public Transfer(final HardwareMap hMap, final String name, Telemetry telemetry) {
        transferMotor = new Motor(hMap, "transferMotor", Motor.GoBILDA.RPM_312);
        switchServo = new ServoEx(hMap, "switchServo", 0, (MAX_ANGLE * 2));

        transferMotor.setRunMode(Motor.RunMode.RawPower);
        switchServo.setCachingTolerance(0.01);
        this.telemetry = telemetry;
    }

    public void periodic() {
        telemetry.addData("Switch Servo Position", getSwitchRotationCorrected());
    }

    public void transfer(double power) {
        transferMotor.set(power);
    }

    // all the servo methods are done with MAX_ANGLE in mind

    public void setSwitchPosition(double angleFromCenter) {
        double mappedAngle = angleFromCenter + MAX_ANGLE;
        switchServo.set(mappedAngle);
    }

    public void moveToNegativePoint() {
        setSwitchPosition(-MAX_ANGLE);
    }

    public void moveToPositivePoint() {
        setSwitchPosition(MAX_ANGLE);
    }

    public void moveSwitchToZero() {
        setSwitchPosition(0);
    }

    public double getSwitchRotation() {
        return switchServo.get() - MAX_ANGLE;
    }

    public String getSwitchRotationCorrected() {
        double rotation = getSwitchRotation();
        if (rotation < -0.5) {
            return "Right";
        } else if (rotation > 0.5) {
            return "Left";
        } else {
            return "Center";
        }
    }
}