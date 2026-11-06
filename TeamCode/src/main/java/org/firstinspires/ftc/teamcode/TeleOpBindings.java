package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.InstantCommand;
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
    private int SHOOTER_DESIRED_RPM = 3000;

    private final GamepadEx driver1;
    private final GamepadEx driver2;
    private final Mythos robot;

    // intake button declarations
    private final GamepadButton intakeIn;
    private final GamepadButton intakeOut;

    // intake transfer combo declarations
    private final GamepadButton intakeTransferIn;
    private final GamepadButton intakeTransferOut;

    // transfer button declarations
    private final GamepadButton transferIn;
    private final GamepadButton transferOut;
    private final GamepadButton switchServoLeft;
    private final GamepadButton switchServoRight;

    // shooter button declarations
    private final Trigger shooterForward;

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
        transferIn = new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP);
        transferOut = new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN);
        switchServoLeft = new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT);
        switchServoRight =  new GamepadButton(driver1, GamepadKeys.Button.DPAD_RIGHT);

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

        // transfer
        switchServoLeft.whileActiveOnce(new InstantCommand(() -> robot.transfer.moveToNegativePoint(), robot.transfer));
        switchServoRight.whileActiveOnce(new InstantCommand(() -> robot.transfer.moveToPositivePoint(), robot.transfer));
        transferIn.whileActiveContinuous(new StartEndCommand(
                () -> robot.transfer.transfer(1),
                () -> robot.transfer.transfer(0)
        ));
        transferOut.whileActiveContinuous(new StartEndCommand(
                () -> robot.transfer.transfer(-1),
                () -> robot.transfer.transfer(0)
        ));

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> robot.shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> robot.shooter.stop()
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