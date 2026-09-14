package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Drivetrain extends SubsystemBase {
    private final Motor frontLeft;
    private final Motor frontRight;
    private final Motor backLeft;
    private final Motor backRight;
    private final MecanumDrive driveBase;


    public Drivetrain(final HardwareMap hMap, final String name) {
        frontLeft = new Motor(hMap, "frontLeft", Motor.GoBILDA.RPM_435);
        frontRight = new Motor(hMap, "frontRight", Motor.GoBILDA.RPM_435);
        backLeft = new Motor(hMap, "backLeft", Motor.GoBILDA.RPM_435);
        backRight = new Motor(hMap, "backRight", Motor.GoBILDA.RPM_435);

        driveBase = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
    }

    public void drive(double strafe, double forward, double rotation) {
        driveBase.driveRobotCentric(strafe, forward, rotation);
    }
}