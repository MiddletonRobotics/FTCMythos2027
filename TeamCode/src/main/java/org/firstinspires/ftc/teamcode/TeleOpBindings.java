package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.StartEndCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.commands.IntakeTransfer;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

public class TeleOpBindings {
    // constants
    private static final int SHOOTER_DESIRED_RPM = 3750;

    private final GamepadEx driver1;
    private final Drivetrain drivetrain;
    private final Intake intake;
    private final Transfer transfer;
    private final Shooter shooter;

    // DRIVER 1
    // Intake
    private final GamepadButton intakeIn;
    private final GamepadButton intakeOut;

    // Transfer
    private final GamepadButton transferSideIn;
    private final GamepadButton transferSideOut;
    private final GamepadButton servoPollen;
    private final GamepadButton servoNectar;
    private final GamepadButton servoMiddle;

    // Combined
    private final Trigger combinedIn;
    private final GamepadButton combinedOut;

    // shooter button declarations
    private final Trigger shooterForward;
    private final GamepadButton cycleBlockers;
    private final GamepadButton pollenBlock;
    private final GamepadButton blockNectar;

    public TeleOpBindings(GamepadEx driver1, GamepadEx driver2, Drivetrain drivetrain, Intake intake, Transfer transfer, Shooter shooter) {
        this.driver1 = driver1;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;

        // DRIVER 1
        // intake
        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.X);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.Y);

        // transfer
        transferSideIn = new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP);
        transferSideOut = new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN);
        servoPollen = new GamepadButton(driver1, GamepadKeys.Button.A);
        servoNectar = new GamepadButton(driver1, GamepadKeys.Button.B);
        servoMiddle = new GamepadButton(driver1, GamepadKeys.Button.LEFT_STICK_BUTTON);

        // Combined
        combinedIn = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1);
        combinedOut = new GamepadButton(driver1, GamepadKeys.Button.LEFT_BUMPER);

        // shooter
        shooterForward = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);
        cycleBlockers = new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT);
        pollenBlock = new GamepadButton(driver1, GamepadKeys.Button.DPAD_RIGHT);
        blockNectar = new GamepadButton(driver1, GamepadKeys.Button.RIGHT_BUMPER);
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
                () -> intake.intake(1),
                () -> intake.intake(0)
        ));
        transferSideOut.whileActiveContinuous(new StartEndCommand(
                () -> intake.intake(-1),
                () -> intake.intake(0)
        ));
        servoPollen.whileActiveOnce(new InstantCommand(() -> transfer.setTransferServo(transfer.SERVO_POLLEN)));
        servoNectar.whileActiveOnce(new InstantCommand(() -> transfer.setTransferServo(transfer.SERVO_NECTAR)));
        servoMiddle.whileActiveOnce(new InstantCommand(() -> transfer.setTransferServo(transfer.SERVO_MIDDLE)));

        // combined
        combinedIn.whileActiveContinuous(new IntakeTransfer(intake, transfer, -1));
        combinedOut.whileActiveContinuous(new IntakeTransfer(intake, transfer, 1));

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> shooter.set(0)
        ));
        cycleBlockers.whileActiveOnce(new InstantCommand(() -> {
            if (shooter.getBlockersClean() == 1) {
                shooter.blockersDown();
            } else {
                shooter.blockersUp();
            }
        }));
        pollenBlock.whileActiveOnce(new InstantCommand(() -> {
            if (shooter.getPollenBlockerClean() == 1) {
                shooter.pollenBlockerDown();
            } else {
                shooter.pollenBlockerUp();
            }
        }));
        blockNectar.whileActiveOnce(new InstantCommand(() -> {
            if (shooter.getNectarBlockerClean() == 1) {
                shooter.nectarBlockerDown();
            } else {
                shooter.nectarBlockerUp();
            }
        }));
    }

    public void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DrivetrainController(
                drivetrain,
                () -> -driver1.getLeftY(),
                () -> driver1.getLeftX(),
                () -> driver1.getRightX()
        ));
    }
}