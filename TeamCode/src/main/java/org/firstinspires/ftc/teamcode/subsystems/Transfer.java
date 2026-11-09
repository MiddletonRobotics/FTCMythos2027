package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer extends SubsystemBase {
    private final Motor transferMotor;

    public Transfer(final HardwareMap hMap, Telemetry telemetry) {
        transferMotor = new Motor(hMap, "transferMotor", Motor.GoBILDA.RPM_312);

        transferMotor.setRunMode(Motor.RunMode.RawPower);
    }

    public void transfer(double power) {
        transferMotor.set(power);
    }
}