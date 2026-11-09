package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

import java.util.function.DoubleSupplier;

public class IntakeTransferCombo extends CommandBase {
    private final Intake intake;
    private final Transfer transfer;
    private DoubleSupplier speedSupplier;
    private double speed;

    public IntakeTransferCombo(Intake intake, Transfer transfer, double speed) {
        this.intake = intake;
        this.transfer = transfer;
        this.speed = speed;
        this.speedSupplier = () -> speed;

        addRequirements(intake, transfer);
    }

    public IntakeTransferCombo(Intake intake, Transfer transfer, DoubleSupplier speedSupplier) {
        this.intake = intake;
        this.transfer = transfer;
        this.speedSupplier = speedSupplier;

        addRequirements(intake, transfer);
    }

    public IntakeTransferCombo(Intake intake, Transfer transfer) {
        this.intake = intake;
        this.transfer = transfer;
        this.speed = 1.0;
        this.speedSupplier = () -> 1.0;

        addRequirements(intake, transfer);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        this.speedSupplier = () -> speed;
    }

    public void run(double speed) {
        setSpeed(speed);
        intake.intake(-speed);
        transfer.transfer(speed);
    }

    public void intakeTransfer(double speed) {
        run(speed);
    }

    @Override
    public void execute() {
        double currentSpeed = speedSupplier.getAsDouble();
        intake.intake(-currentSpeed);
        transfer.transfer(currentSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.intake(0);
        transfer.transfer(0);
    }
}
