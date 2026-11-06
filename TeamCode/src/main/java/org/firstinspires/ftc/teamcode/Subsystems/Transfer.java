package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class Transfer extends SubsystemBase {
    private final Motor transferMotor;
    private final ServoEx switchServo;

    // max offset angle for switch servo
    private static final double MAX_ANGLE = 15.223947696;

    // servo switch angle is 15.223947696 degrees
    public Transfer(final HardwareMap hMap, final String name) {
        transferMotor = new Motor(hMap, "transferMotor", Motor.GoBILDA.RPM_312);
        switchServo = new ServoEx(hMap, "switchServo", 0, (MAX_ANGLE * 2));

        transferMotor.setRunMode(Motor.RunMode.RawPower);
        switchServo.setCachingTolerance(0.01);
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

    public double getSwitchRotation() {
        return switchServo.get() - MAX_ANGLE;
    }
}