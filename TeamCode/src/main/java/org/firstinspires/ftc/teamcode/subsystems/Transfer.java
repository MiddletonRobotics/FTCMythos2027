package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer extends SubsystemBase {
    private final Motor transferMotor;

    // max offset angle for switch servo
    private static final double MAX_ANGLE = 14.75;

    // servo switch angle is 15.223947696 degrees
    public Transfer(final HardwareMap hMap, Telemetry telemetry) {
        transferMotor = new Motor(hMap, "transferMotor", Motor.GoBILDA.RPM_312);

        transferMotor.setRunMode(Motor.RunMode.RawPower);
    }

    public void transfer(double power) {
        transferMotor.set(power);
    }
}