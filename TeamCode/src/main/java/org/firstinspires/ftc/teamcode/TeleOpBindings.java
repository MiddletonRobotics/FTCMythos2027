package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.StartEndCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

public class TeleOpBindings {
    // constants
    private int SHOOTER_DESIRED_RPM = 3300;

    private final GamepadEx driver1;
    private final Drivetrain drivetrain;
    private final Intake intake;
    private final Transfer transfer;
    private final Shooter shooter;

    // DRIVER 1
    // intake button declarations
    private final GamepadButton intakeIn;
    private final GamepadButton intakeOut;

    // shooter button declarations
    private final Trigger shooterForward;
    private final GamepadButton setPollenZero;
    private final GamepadButton setPollenOne;

    public TeleOpBindings(GamepadEx driver1, GamepadEx driver2, Drivetrain drivetrain, Intake intake, Transfer transfer, Shooter shooter) {
        this.driver1 = driver1;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;

        // DRIVER 1
        // intake
        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.Y);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.X);

        // transfer

        // shooter
        shooterForward = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);
        setPollenZero = new GamepadButton(driver1, GamepadKeys.Button.LEFT_BUMPER);
        setPollenOne = new GamepadButton(driver1, GamepadKeys.Button.RIGHT_BUMPER);
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

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> shooter.set(0)
        ));
        setPollenZero.whileActiveOnce(new InstantCommand(() -> shooter.setPollenBlocker(0)));
        setPollenOne.whileActiveOnce(new InstantCommand(() -> shooter.setPollenBlocker(1)));
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