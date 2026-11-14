package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.MotorMath;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

@Config
@Configurable
public class Shooter extends SubsystemBase {
    private final MotorEx shooter;
    private final ServoEx pollenBlocker;
    private final ServoEx nectarBlocker;
    private final VoltageSensor voltageSensor;
    private final Telemetry telemetry;

    public static double kp = 1.25;
    public static double ki = 0.0;
    public static double kd = 0.2;
    public static double ks = 0.0;
    public static double kv = 0.0;

    public static double nominalVoltage = 13.0;

    public Shooter(final HardwareMap hMap, Telemetry telemetry) {
        shooter = new MotorEx(hMap, "shooter", Motor.GoBILDA.BARE);
        pollenBlocker = new ServoEx(hMap, "pollenBlocker", 0, 355);
        nectarBlocker = new ServoEx(hMap, "nectarBlocker", 0, 355);
        voltageSensor = hMap.voltageSensor.iterator().next();

        shooter.setRunMode(Motor.RunMode.VelocityControl);

        // set starting PID and feedforward coefficients so velocity control functions
        setVeloCoefficients(kp, ki, kd);
        setFeedforwardCoefficients(ks, kv);

        this.telemetry = telemetry;
    }

    public void periodic() {
        setVeloCoefficients(kp, ki, kd);
        setFeedforwardCoefficients(ks, kv);

        double ticksPerSecond = shooter.getCorrectedVelocity();
        double rpm = MotorMath.ticksPerSecondToRPM(ticksPerSecond, MotorMath.GoBildaMotor.RPM_6000);

        telemetry.addData("Shooter RPM", rpm);
    }

    public void setPollenBlocker(double angle) {
        pollenBlocker.set(angle);
    }

    public void setNectarBlocker(double angle) {
        nectarBlocker.set(angle);
    }

    public void setVelocity(double rpm) {
        double ticksPerSec = (rpm * shooter.getCPR()) / 60.0;
        shooter.setVelocity(ticksPerSec);
    }

    public void set(double power) {
        shooter.setRunMode(Motor.RunMode.RawPower);
        shooter.set(power);
        shooter.setRunMode(Motor.RunMode.VelocityControl);
    }

    public void setVeloCoefficients(double kp, double ki, double kd) {
        shooter.setVeloCoefficients(kp, ki, kd);
    }

    public void setFeedforwardCoefficients(double ks, double kv) {
        double currentVoltage = voltageSensor.getVoltage();
        double voltageScale = (currentVoltage > 0 && nominalVoltage > 0) ? (nominalVoltage / currentVoltage) : 1.0;
        shooter.setFeedforwardCoefficients(ks, kv * voltageScale);
    }
}
