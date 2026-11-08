package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class DrivetrainController extends CommandBase {

    private final Drivetrain drivetrain;
    private final DoubleSupplier forwardSupplier;
    private final DoubleSupplier strafeSupplier;
    private final DoubleSupplier rotationSupplier;

    public DrivetrainController(
            Drivetrain subsystem,
            DoubleSupplier forward,
            DoubleSupplier strafe,
            DoubleSupplier rotation) {

        drivetrain = subsystem;
        forwardSupplier = forward;
        strafeSupplier = strafe;
        rotationSupplier = rotation;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.drive(
                forwardSupplier.getAsDouble(),
                strafeSupplier.getAsDouble(),
                rotationSupplier.getAsDouble()
        );
    }
}
