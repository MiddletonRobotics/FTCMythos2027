package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter extends SubsystemBase {
    private final MotorEx shooter;
    private final Telemetry telemetry;

    // Default starting constants for velocity control (tune later as needed)
    private static final double DEFAULT_KP = 1.0;
    private static final double DEFAULT_KI = 0.0;
    private static final double DEFAULT_KD = 0.0;
    private static final double DEFAULT_KS = 0.0;
    private static final double DEFAULT_KV = 0.0;

    public Shooter(final HardwareMap hMap, final String name, Telemetry telemetry) {
        shooter = new MotorEx(hMap, "shooter", Motor.GoBILDA.BARE);

        shooter.setRunMode(Motor.RunMode.VelocityControl);

        // set starting PID and feedforward coefficients so velocity control functions
        setVeloCoefficients(DEFAULT_KP, DEFAULT_KI, DEFAULT_KD);
        setFeedforwardCoefficients(DEFAULT_KS, DEFAULT_KV);

        this.telemetry = telemetry;
    }

    public void periodic() {
        double ticksPerSecond = shooter.getCorrectedVelocity();
        double rpm = (ticksPerSecond / shooter.getCPR()) * 60;

        telemetry.addData("Shooter RPM", rpm);
    }

    public void setVelocity(double rpm) {
        double ticksPerSec = (rpm * shooter.getCPR()) / 60.0;
        shooter.setVelocity(ticksPerSec);
    }

    // backward compatible / raw power method
    public void set(double power) {
        shooter.set(power);
    }

    public void setVeloCoefficients(double kp, double ki, double kd) {
        shooter.setVeloCoefficients(kp, ki, kd);
    }

    public void setFeedforwardCoefficients(double ks, double kv) {
        shooter.setFeedforwardCoefficients(ks, kv);
    }
}
