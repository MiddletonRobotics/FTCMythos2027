package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
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

    private final Telemetry telemetry;

    public Transfer(final HardwareMap hMap, Telemetry telemetry) {
        nectarMotor = new Motor(hMap, "nectarMotor", Motor.GoBILDA.RPM_312);
        pollenMotor = new Motor(hMap, "pollenMotor", Motor.GoBILDA.RPM_312);
        transferServo = new ServoEx(hMap, "transferServo", 0, 355);

        nectarMotor.setRunMode(Motor.RunMode.RawPower);
        pollenMotor.setRunMode(Motor.RunMode.RawPower);

        this.telemetry = telemetry;
    }

    public void periodic() {
        String servoState;
        int cleanState = getTransferServoClean();
        if (cleanState == 1) {
            servoState = "Pollen";
        } else if (cleanState == 2) {
            servoState = "Nectar";
        } else {
            servoState = "Middle";
        }
        telemetry.addData("Transfer Servo State", servoState);
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

    public double getTransferServo() {
        return transferServo.get();
    }

    // one is for pollen
    // two is for nectar
    // three is for middle
    public int getTransferServoClean() {
       if (Math.abs(getTransferServo() - SERVO_POLLEN) < 1.0) {
           return 1;
       } else if (Math.abs(getTransferServo() - SERVO_NECTAR) < 1.0) {
           return 2;
       } else {
           return 3;
       }
    }
}