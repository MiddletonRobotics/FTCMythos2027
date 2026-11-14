package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

public class IntakeTransfer extends CommandBase {
    public enum TransferMotor {
        POLLEN,
        NECTAR,
        BOTH,
        NONE
    }

    private final Intake intake;
    private final Transfer transfer;

    private final double intakeSpeed;
    private final double transferSpeed;
    private TransferMotor transferMotor;

    public IntakeTransfer(Intake intake, Transfer transfer, double speed) {
        this.intake = intake;
        this.transfer = transfer;
        this.intakeSpeed = speed;
        this.transferSpeed = speed;
        this.transferMotor = TransferMotor.BOTH;

        addRequirements(intake, transfer);
    }

    public IntakeTransfer(Intake intake, Transfer transfer, double speed, TransferMotor transferMotor) {
        this.intake = intake;
        this.transfer = transfer;
        this.intakeSpeed = speed;
        this.transferSpeed = speed;
        this.transferMotor = transferMotor;

        addRequirements(intake, transfer);
    }

    public IntakeTransfer(Intake intake, Transfer transfer, double intakeSpeed, double transferSpeed, TransferMotor transferMotor) {
        this.intake = intake;
        this.transfer = transfer;
        this.intakeSpeed = intakeSpeed;
        this.transferSpeed = transferSpeed;
        this.transferMotor = transferMotor;

        addRequirements(intake, transfer);
    }

    @Override
    public void initialize() {
        int clean = transfer.getTransferServoClean();
        if (clean == 1) {
            transferMotor = TransferMotor.POLLEN;
        } else if (clean == 2) {
            transferMotor = TransferMotor.NECTAR;
        } else {
            transferMotor = TransferMotor.BOTH;
        }
        runMotors();
    }

    @Override
    public void execute() {
        runMotors();
    }

    @Override
    public void end(boolean interrupted) {
        intake.intake(0);
        transfer.setPollenMotor(0);
        transfer.setNectarMotor(0);
    }

    private void runMotors() {
        intake.intake(intakeSpeed);
        switch (transferMotor) {
            case POLLEN:
                transfer.setPollenMotor(transferSpeed);
                transfer.setNectarMotor(0);
                break;
            case NECTAR:
                transfer.setNectarMotor(transferSpeed);
                transfer.setPollenMotor(0);
                break;
            case BOTH:
                transfer.setTransferMotors(transferSpeed);
                break;
            case NONE:
                transfer.setPollenMotor(0);
                transfer.setNectarMotor(0);
                break;
        }
    }
}
