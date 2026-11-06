package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mythos {
    public final Drivetrain drivetrain;
    public final Intake intake;
    public final Transfer transfer;
    public final Shooter shooter;

    public Mythos(HardwareMap hardwareMap, Telemetry telemetry) {
        drivetrain = new Drivetrain(hardwareMap, "Drivetrain");
        intake = new Intake(hardwareMap, "Intake");
        transfer = new Transfer(hardwareMap, "Transfer", telemetry);
        shooter = new Shooter(hardwareMap, "Shooter", telemetry);
    }
}