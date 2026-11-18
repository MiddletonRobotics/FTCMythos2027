package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.StartEndCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.commands.IntakeTransfer;
import org.firstinspires.ftc.teamcode.subsystems.Depollinator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

public class TeleOpBindings {
    // constants
    private static final int SHOOTER_DESIRED_RPM = 3500;

    private final GamepadEx driver1;
    private final Drivetrain drivetrain;
    private final Intake intake;
    private final Depollinator depollinator;
    private final Transfer transfer;
    private final Shooter shooter;

    // DRIVER 1
    // Intake
    private final GamepadButton intakeIn;
    private final GamepadButton intakeOut;

    // Transfer
    private final GamepadButton transferSideIn;
    private final GamepadButton transferSideOut;
    private final GamepadButton transferForward;
    private final GamepadButton transferToggle;

    // Combined
    private final Trigger combinedIn;
    private final GamepadButton combinedOut;

    // depollinate
    private final GamepadButton depollinateToggle;

    // shooter button declarations
    private final Trigger shooterForward;
    private final GamepadButton cycleBlockers;

    public TeleOpBindings(GamepadEx driver1, GamepadEx driver2, Drivetrain drivetrain, Intake intake, Depollinator depollinator, Transfer transfer, Shooter shooter) {
        this.driver1 = driver1;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.depollinator = depollinator;
        this.transfer = transfer;
        this.shooter = shooter;

        // DRIVER 1
        // intake
        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.X);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.Y);

        // transfer
        transferSideIn = new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP);
        transferSideOut = new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN);
        transferForward = new GamepadButton(driver1, GamepadKeys.Button.RIGHT_BUMPER);
        transferToggle = new GamepadButton(driver1, GamepadKeys.Button.A);

        // Combined
        combinedIn = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1);
        combinedOut = new GamepadButton(driver1, GamepadKeys.Button.LEFT_BUMPER);

        // depollinate
        depollinateToggle = new GamepadButton(driver1, GamepadKeys.Button.B);

        // shooter
        shooterForward = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);
        cycleBlockers = new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT);
    }

    public void controlMap() {
        // DRIVER 1
        // intake
        intakeIn.whileActiveContinuous(new StartEndCommand(
                () -> intake.intake(1),
                () -> intake.intake(0)
        ));
        intakeOut.whileActiveContinuous(new StartEndCommand(
                () -> intake.intake(-1),
                () -> intake.intake(0)
        ));

        // Transfer
        transferSideIn.whileActiveContinuous(new StartEndCommand(
                () -> {
                    if (transfer.isNectar()) {
                        transfer.setNectarMotor(-1);
                    } else {
                        transfer.setPollenMotor(-1);
                    }
                },
                () -> transfer.setTransferMotors(0)
        ));
        transferSideOut.whileActiveContinuous(new StartEndCommand(
                () -> {
                    if (transfer.isNectar()) {
                        transfer.setNectarMotor(1);
                    } else {
                        transfer.setPollenMotor(1);
                    }
                },
                () -> transfer.setTransferMotors(0)
        ));
        transferForward.whileActiveContinuous(new StartEndCommand(
                () -> transfer.setTransferMotors(-1),
                () -> transfer.setTransferMotors(0)
        ));
        transferToggle.whileActiveOnce(new InstantCommand(transfer::toggleTransferServo));

        // combined
        combinedIn.whileActiveContinuous(new IntakeTransfer(intake, transfer, -1));
        combinedOut.whileActiveContinuous(new IntakeTransfer(intake, transfer, 1));

        // depolliate
        depollinateToggle.whileActiveOnce(new InstantCommand(depollinator::toggleDepollinate));

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> shooter.set(0)
        ));
        cycleBlockers.whileActiveOnce(new InstantCommand(shooter::toggleBlockers));
    }

    public void configureDefaultCommands(double headingOffset) {
        drivetrain.setDefaultCommand(new DrivetrainController(
                drivetrain,
                () -> -driver1.getLeftY(),
                () -> driver1.getLeftX(),
                () -> driver1.getRightX(),
                headingOffset
        ));
    }
}
