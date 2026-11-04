package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter extends SubsystemBase {
    private final Motor pollenFly;
    private final Motor nectarFly;

    private final Telemetry telemetry;

    // this is assuming that the robot is viewed from my intake forward
    public Shooter(final HardwareMap hMap, final String name, Telemetry telemetry) {
        pollenFly = new Motor(hMap, "pollenFly", Motor.GoBILDA.BARE);
        nectarFly = new Motor(hMap, "nectarFly", Motor.GoBILDA.BARE);

        pollenFly.setRunMode(Motor.RunMode.RawPower);
        nectarFly.setRunMode(Motor.RunMode.RawPower);

        this.telemetry = telemetry;
    }

    public void periodic() {
        double pollenTicksPerSecond = pollenFly.getCorrectedVelocity();
        double nectarTicksPerSecond = nectarFly.getCorrectedVelocity();

        double pollenRPM = (pollenTicksPerSecond / pollenFly.getCPR()) * 60;
        double nectarRPM = (nectarTicksPerSecond / nectarFly.getCPR()) * 60;

        telemetry.addData("Pollen RPM", pollenRPM);
        telemetry.addData("Nectar RPM", nectarRPM);
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