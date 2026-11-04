package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

public class TransferOnShooterRPM extends CommandBase {
    private final Transfer transfer;
    private final Shooter shooter;

    public TransferOnShooterRPM(Transfer transfer, Shooter shooter) {
        this.transfer = transfer;
        this.shooter = shooter;
    }
}
