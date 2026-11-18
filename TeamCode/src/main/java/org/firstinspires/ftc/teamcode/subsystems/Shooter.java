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
    private final Double SERVO_ANGLE = 90.0;

    private final MotorEx shooter;
    private final ServoEx pollenBlocker;
    private final ServoEx nectarBlocker;
    private final ServoEx indicator;
    private final VoltageSensor voltageSensor;
    private final Telemetry telemetry;

    private boolean areBlockersDown = false;

    public static double kp = 1.25;
    public static double ki = 0.0;
    public static double kd = 0.2;
    public static double ks = 0.0;
    public static double kv = 0.0;

    public static double nominalVoltage = 13.0;

    public enum LightState {
        OFF(0.0),
        WHITE(1.0),
        RED(0.28),
        BLUE(0.666),
        PURPLE(0.722),
        YELLOW(0.388);

        public final double pwmValue;
        LightState(double pwmValue) {
            this.pwmValue = pwmValue;
        }
    }

    public Shooter(final HardwareMap hMap, Telemetry telemetry) {
        shooter = new MotorEx(hMap, "shooter", Motor.GoBILDA.BARE);
        pollenBlocker = new ServoEx(hMap, "pollenBlocker", 0, 355);
        nectarBlocker = new ServoEx(hMap, "nectarBlocker", 0, 355);
        indicator = new ServoEx(hMap, "indicator");
        voltageSensor = hMap.voltageSensor.iterator().next();

        shooter.setRunMode(Motor.RunMode.VelocityControl);

        setVeloCoefficients(kp, ki, kd);
        setFeedforwardCoefficients(ks, kv);

        blockersUp();

        this.telemetry = telemetry;
    }

    public void periodic() {
        setVeloCoefficients(kp, ki, kd);
        setFeedforwardCoefficients(ks, kv);

        double ticksPerSecond = shooter.getCorrectedVelocity();
        double rpm = MotorMath.ticksPerSecondToRPM(ticksPerSecond, MotorMath.GoBildaMotor.RPM_6000);

        telemetry.addData("Shooter RPM", rpm);
        telemetry.addData("Blockers State", areBlockersDown ? "Down" : "Up");
    }

    public void pollenBlockerUp() {
        pollenBlocker.set(0);
    }

    public void indicatorSet(LightState state) {
        indicator.set(state.pwmValue);
    }

    public void pollenBlockerDown() {
        pollenBlocker.set(SERVO_ANGLE);
    }

    public void nectarBlockerUp() {
        nectarBlocker.set(SERVO_ANGLE);
    }

    public void nectarBlockerDown() {
        nectarBlocker.set(0);
    }

    public void blockersDown() {
        pollenBlockerDown();
        nectarBlockerDown();
        areBlockersDown = true;
    }

    public void blockersUp() {
        pollenBlockerUp();
        nectarBlockerUp();
        areBlockersDown = false;
    }

    public void toggleBlockers() {
        areBlockersDown = !areBlockersDown;
        if (areBlockersDown) {
            blockersDown();
        } else {
            blockersUp();
        }
    }

    public boolean isAreBlockersDown() {
        return areBlockersDown;
    }

    public double getNectarBlocker() {
        return nectarBlocker.get();
    }

    public double getPollenBlocker() {
        return pollenBlocker.get();
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
