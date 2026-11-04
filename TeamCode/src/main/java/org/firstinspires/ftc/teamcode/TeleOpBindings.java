package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.StartEndCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.Commands.IntakeTransferCombo;
import org.firstinspires.ftc.teamcode.Subsystems.Mythos;

public class TeleOpBindings {
    // constants
    private final int SHOOTER_DESIRED_RPM = 2000;

    private final GamepadEx driver1;
    private final GamepadEx driver2;
    private final Mythos robot;

    // intake button declarations
    private final GamepadButton intakeIn;
    private final GamepadButton intakeOut;

    // intake transfer combo declarations
    private final GamepadButton intakeTransferIn;
    private final GamepadButton intakeTransferOut;

    // shooter button declarations
    private final Trigger shooterForward;
    // private final Trigger shooterAutomated; // for when I get the transfer automation done

    public TeleOpBindings(GamepadEx driver1, GamepadEx driver2, Mythos robot) {
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.robot = robot;

        // intake
        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.Y);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.X);

        // intake transfer combo

        intakeTransferIn = new GamepadButton(driver1, GamepadKeys.Button.A);
        intakeTransferOut = new GamepadButton(driver1, GamepadKeys.Button.B);

        // transfer

        // shooter
        shooterForward = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);
    }

    public void controlMap() {
        // intake
        intakeIn.whileActiveContinuous(new StartEndCommand(
                () -> robot.intake.intake(1),
                () -> robot.intake.intake(0)
        ));
        intakeOut.whileActiveContinuous(new StartEndCommand(
                () -> robot.intake.intake(-1),
                () -> robot.intake.intake(0)
        ));

        // intake transfer combo
        intakeTransferIn.whileActiveContinuous(new IntakeTransferCombo(robot.intake, robot.transfer, 1.0));
        intakeTransferOut.whileActiveContinuous(new IntakeTransferCombo(robot.intake, robot.transfer, -1.0));

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> robot.shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> robot.shooter.set(0)
        ));
    }

    public void configureDefaultCommands() {
        robot.drivetrain.setDefaultCommand(new DrivetrainController(
                robot.drivetrain,
                () -> -driver1.getLeftY(),
                () -> -driver1.getRightX(),
                () -> -driver1.getLeftX()
        ));
    }
}
