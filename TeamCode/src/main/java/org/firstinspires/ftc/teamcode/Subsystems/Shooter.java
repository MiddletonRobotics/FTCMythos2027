package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Shooter extends SubsystemBase {
    private final Motor pollenFly;
    private final Motor nectarFly;

    // this is assuming that the robot is viewed from my intake forward
    public Shooter(final HardwareMap hMap, final String name) {
        pollenFly = new Motor(hMap, "pollenFly", Motor.GoBILDA.RPM_312);
        nectarFly = new Motor(hMap, "nectarFly", Motor.GoBILDA.RPM_312);

        pollenFly.setRunMode(Motor.RunMode.RawPower);
        nectarFly.setRunMode(Motor.RunMode.RawPower);
    }

    public void setPollenFly(double power) {
        pollenFly.set(power);
    }

    public void setNectarFly(double power) {
        nectarFly.set(power);
    }

    public void setShooter(double power) {
        pollenFly.set(power);
        nectarFly.set(power);
    }
}