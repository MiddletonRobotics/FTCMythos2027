package org.firstinspires.ftc.teamcode;

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
    private static final int SHOOTER_DESIRED_RPM = 3300;

    private final GamepadEx driver1;
    private final Drivetrain drivetrain;
    private final Intake intake;
    private final Transfer transfer;
    private final Shooter shooter;

    // DRIVER 1
    // intake button declarations
    private final Trigger intakeIn;
    private final GamepadButton intakeOut;

    private final GamepadButton transferSideIn;
    private final GamepadButton transferSideOut;
    private final GamepadButton transferIn;
    private final GamepadButton transferOut;

    // shooter button declarations
    private final Trigger shooterForward;

    public TeleOpBindings(GamepadEx driver1, GamepadEx driver2, Drivetrain drivetrain, Intake intake, Transfer transfer, Shooter shooter) {
        this.driver1 = driver1;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;

        // DRIVER 1
        // intake
        intakeIn = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.LEFT_BUMPER);

        // transfer
        transferSideIn = new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP);
        transferSideOut = new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN);
        transferIn = new GamepadButton(driver1, GamepadKeys.Button.Y);
        transferOut = new GamepadButton(driver1, GamepadKeys.Button.X);

        // shooter
        shooterForward = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1);
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

        // transfer
        transferSideIn.whileActiveContinuous(new IntakeTransfer(intake, transfer, 1));
        transferSideOut.whileActiveContinuous(new IntakeTransfer(intake, transfer, -1));
        transferIn.whileActiveContinuous(new IntakeTransfer(intake, transfer, 1, IntakeTransfer.TransferMotor.BOTH));
        transferOut.whileActiveContinuous(new IntakeTransfer(intake, transfer, -1, IntakeTransfer.TransferMotor.BOTH));

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> shooter.set(0)
        ));
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