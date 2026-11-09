package org.firstinspires.ftc.teamcode.utilities;

public class MotorMath {

    public enum GoBildaMotor {
        RPM_6000(28.0),
        RPM_1620(103.8),
        RPM_1150(145.1),
        RPM_435(384.5),
        RPM_312(537.7),
        RPM_223(751.8),
        RPM_117(1425.1),
        RPM_84(1993.6),
        RPM_60(2786.2),
        RPM_43(3895.9),
        RPM_30(5281.1);

        public final double cpr;

        GoBildaMotor(double cpr) {
            this.cpr = cpr;
        }
    }

    public static double rpmToTicksPerSecond(double rpm, GoBildaMotor motor) {
        return rpmToTicksPerSecond(rpm, motor.cpr);
    }

    public static double ticksPerSecondToRPM(double ticksPerSecond, GoBildaMotor motor) {
        return ticksPerSecondToRPM(ticksPerSecond, motor.cpr);
    }

    public static double rpmToTicksPerSecond(double rpm, double cpr) {
        return (rpm * cpr) / 60.0;
    }

    public static double ticksPerSecondToRPM(double ticksPerSecond, double cpr) {
        return (ticksPerSecond / cpr) * 60;
    }
}
