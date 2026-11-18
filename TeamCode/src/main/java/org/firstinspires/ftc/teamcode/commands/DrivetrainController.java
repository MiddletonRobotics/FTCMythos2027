package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class DrivetrainController extends CommandBase {

    private final Drivetrain drivetrain;
    private final DoubleSupplier forwardSupplier;
    private final DoubleSupplier strafeSupplier;
    private final DoubleSupplier rotationSupplier;
    private final double headingOffset;

    public DrivetrainController(
            Drivetrain subsystem,
            DoubleSupplier forward,
            DoubleSupplier strafe,
            DoubleSupplier rotation,
            double headingOffset) {

        drivetrain = subsystem;
        forwardSupplier = forward;
        strafeSupplier = strafe;
        rotationSupplier = rotation;
        this.headingOffset = headingOffset;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.drive(
                forwardSupplier.getAsDouble(),
                strafeSupplier.getAsDouble(),
                rotationSupplier.getAsDouble(),
                headingOffset
        );
    }
}
