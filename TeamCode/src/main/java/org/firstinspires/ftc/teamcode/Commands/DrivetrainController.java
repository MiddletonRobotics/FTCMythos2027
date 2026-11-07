package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class DrivetrainController extends CommandBase {

    private final Drivetrain drivetrain;
    private final DoubleSupplier d_forward;
    private final DoubleSupplier d_strafe;
    private final DoubleSupplier d_rotation;

    public DrivetrainController(
            Drivetrain subsystem,
            DoubleSupplier forward,
            DoubleSupplier strafe,
            DoubleSupplier rotation) {

        drivetrain = subsystem;
        d_forward = forward;
        d_strafe = strafe;
        d_rotation = rotation;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.drive(
                d_forward.getAsDouble(),
                d_strafe.getAsDouble(),
                d_rotation.getAsDouble()
        );
    }
}
