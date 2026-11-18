package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer extends SubsystemBase {
    public final Double SERVO_NECTAR = (73.0 * 0.8);
    public final Double SERVO_MIDDLE = (30.0 * 0.8);
    public final Double SERVO_POLLEN = 0.0;

    private final Motor nectarMotor;
    private final Motor pollenMotor;
    private final ServoEx transferServo;

    private final Intake intake;
    private final Shooter shooter;
    private final Telemetry telemetry;

    private boolean isNectar = false;

    public Transfer(final HardwareMap hMap, Intake intake, Shooter shooter, Telemetry telemetry) {
        nectarMotor = new Motor(hMap, "nectarMotor", Motor.GoBILDA.RPM_312);
        pollenMotor = new Motor(hMap, "pollenMotor", Motor.GoBILDA.RPM_312);
        transferServo = new ServoEx(hMap, "transferServo", 0, 355);

        nectarMotor.setRunMode(Motor.RunMode.RawPower);
        pollenMotor.setRunMode(Motor.RunMode.RawPower);

        this.intake = intake;
        this.shooter = shooter;
        this.telemetry = telemetry;

        // init transfer servo to pollen
        setTransferServo(SERVO_POLLEN);
    }

    public void periodic() {
        // transfer servo update from color sensor
        Intake.BallColor color = intake.getBallColor();
        if (color == Intake.BallColor.POLLEN) {
            setTransferServo(SERVO_POLLEN);
            isNectar = false;
        } else if (color == Intake.BallColor.NECTAR) {
            setTransferServo(SERVO_NECTAR);
            isNectar = true;
        }

        telemetry.addData("Transfer State", isNectar ? "Nectar" : "Pollen");
    }

    public void toggleTransferServo() {
        isNectar = !isNectar;
        if (isNectar) {
            setTransferServo(SERVO_NECTAR);
            shooter.indicatorSet(Shooter.LightState.PURPLE);
        } else {
            setTransferServo(SERVO_POLLEN);
            shooter.indicatorSet(Shooter.LightState.YELLOW);
        }
    }

    public boolean isNectar() {
        return isNectar;
    }

    public void setPollenMotor(double power) {
        pollenMotor.set(power);
    }

    public void setNectarMotor(double power) {
        nectarMotor.set(-power);
    }

    public void setTransferMotors(double power) {
        setNectarMotor(power);
        setPollenMotor(power);
    }

    public void setTransferServo(double angle) {
        transferServo.set(angle);
    }
}
