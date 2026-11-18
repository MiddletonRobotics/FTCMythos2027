package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.SensorRevColorV3;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake extends SubsystemBase {
    private final Motor intakeMotor;
    private final SensorRevColorV3 intakeColor;
    private final Telemetry telemetry;

    // possible colors for color sensor
    public enum BallColor {
        POLLEN,
        NECTAR,
        UNKNOWN
    }

    public Intake(final HardwareMap hMap, Telemetry telemetry) {
        intakeMotor = new Motor(hMap, "intakeMotor", Motor.GoBILDA.RPM_435);
        intakeColor = new SensorRevColorV3(hMap, "intakeColor");
        this.telemetry = telemetry;

        intakeMotor.setRunMode(Motor.RunMode.RawPower);
    }

    public void intake(double power) {
        intakeMotor.set(power);
    }

    @Override
    public void periodic() {
        int red = intakeColor.red();
        int green = intakeColor.green();
        int blue = intakeColor.blue();

        telemetry.addData("Intake Red", red);
        telemetry.addData("Intake Green", green);
        telemetry.addData("Intake Blue", blue);
        telemetry.addData("Intake Color", getBallColor().name());
    }

    public BallColor getBallColor() {
        int red = intakeColor.red();
        int green = intakeColor.green();
        int blue = intakeColor.blue();

        if (red < 100 && green < 100 && blue < 100) {
            return BallColor.UNKNOWN;
        } else if (red > 300 && green > 800 && blue > 300) {
            return BallColor.POLLEN;
        } else if ((red > 150 && green > 300 && blue > 800) || (red > 800 && green > 300 && blue > 150)) {
            return BallColor.NECTAR;
        } else {
            return BallColor.UNKNOWN;
        }
    }
}