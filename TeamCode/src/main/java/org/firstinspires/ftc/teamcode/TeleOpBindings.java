package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.StartEndCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.commands.IntakeTransferCombo;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

public class TeleOpBindings {
    // constants
    private int SHOOTER_DESIRED_RPM = 3000;

    private final GamepadEx driver1;
    private final GamepadEx driver2;
    private final Drivetrain drivetrain;
    private final Intake intake;
    private final Transfer transfer;
    private final Shooter shooter;

    // DRIVER 1
    // intake button declarations
    private final GamepadButton intakeIn;
    private final GamepadButton intakeOut;

    // intake transfer combo declarations
    private final GamepadButton intakeTransferIn;
    private final GamepadButton intakeTransferOut;

    // transfer button declarations
    private final GamepadButton transferIn;
    private final GamepadButton transferOut;
    // private final GamepadButton switchServoLeft;
    // private final GamepadButton switchServoRight;

    // shooter button declarations
    private final Trigger shooterForward;

    // DRIVER 2
    // intake
    private final GamepadButton D2IntakeIn;
    private final GamepadButton D2IntakeOut;

    // intake transfer combo
    private final Trigger D2IntakeTransferIn;
    private final Trigger D2IntakeTransferOut;

    // transfer
    private final GamepadButton D2TransferIn;
    private final GamepadButton D2TransferOut;
    // private final Trigger D2SwitchServoLeft;
    // private final GamepadButton D2SwitchServoRight;
    private final GamepadButton ServoReset;

    // shooter
    private final Trigger D2ShooterForward;
    private final GamepadButton shooterSpeedUp;
    private final GamepadButton shooterSpeedDown;

    public TeleOpBindings(GamepadEx driver1, GamepadEx driver2, Drivetrain drivetrain, Intake intake, Transfer transfer, Shooter shooter) {
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;

        // DRIVER 1
        // intake
        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.Y);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.X);

        // intake transfer combo
        intakeTransferIn = new GamepadButton(driver1, GamepadKeys.Button.A);
        intakeTransferOut = new GamepadButton(driver1, GamepadKeys.Button.B);

        // transfer
        transferIn = new GamepadButton(driver1, GamepadKeys.Button.DPAD_UP);
        transferOut = new GamepadButton(driver1, GamepadKeys.Button.DPAD_DOWN);
        // switchServoLeft = new GamepadButton(driver1, GamepadKeys.Button.DPAD_LEFT);
        // switchServoRight =  new GamepadButton(driver1, GamepadKeys.Button.DPAD_RIGHT);

        // shooter
        shooterForward = new Trigger(() -> driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);

        // DRIVER 2
        // intake
        D2IntakeIn = new GamepadButton(driver2, GamepadKeys.Button.DPAD_LEFT);
        D2IntakeOut = new GamepadButton(driver2, GamepadKeys.Button.DPAD_DOWN);

        // intake transfer combo
        D2IntakeTransferIn = new Trigger(() -> driver2.getRightY() < -0.2);
        D2IntakeTransferOut = new Trigger(() -> driver2.getRightY() > 0.2);

        // transfer
        D2TransferIn = new GamepadButton(driver2, GamepadKeys.Button.DPAD_UP);
        D2TransferOut = new GamepadButton(driver2, GamepadKeys.Button.DPAD_RIGHT);
        // D2SwitchServoLeft = new Trigger(() -> driver2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1);
        // D2SwitchServoRight = new GamepadButton(driver2, GamepadKeys.Button.LEFT_BUMPER);
        ServoReset =  new GamepadButton(driver2, GamepadKeys.Button.LEFT_STICK_BUTTON);

        // shooter
        D2ShooterForward = new Trigger(() -> driver2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);

        // shooter decrease and increase
        shooterSpeedUp = new GamepadButton(driver2, GamepadKeys.Button.B);
        shooterSpeedDown = new GamepadButton(driver2, GamepadKeys.Button.A);
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

        // intake transfer combo
        intakeTransferIn.whileActiveContinuous(new IntakeTransferCombo(intake, transfer, 1.0));
        intakeTransferOut.whileActiveContinuous(new IntakeTransferCombo(intake, transfer, -1.0));

        // transfer
        // switchServoLeft.whileActiveOnce(new InstantCommand(() -> transfer.moveToNegativePoint(), transfer));
        // switchServoRight.whileActiveOnce(new InstantCommand(() -> transfer.moveToPositivePoint(), transfer));
        transferIn.whileActiveContinuous(new StartEndCommand(
                () -> transfer.transfer(1),
                () -> transfer.transfer(0)
        ));
        transferOut.whileActiveContinuous(new StartEndCommand(
                () -> transfer.transfer(-1),
                () -> transfer.transfer(0)
        ));

        // shooter
        shooterForward.whileActiveContinuous(new StartEndCommand(
                () -> shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> shooter.set(0)
        ));

        // DRIVER 2
        // intake
        D2IntakeIn.whileActiveContinuous(new StartEndCommand(
                () -> intake.intake(1),
                () -> intake.intake(0)
        ));
        D2IntakeOut.whileActiveContinuous(new StartEndCommand(
                () -> intake.intake(-1),
                () -> intake.intake(0)
        ));

        // intake transfer combo (right stick)
        D2IntakeTransferIn.whileActiveContinuous(new IntakeTransferCombo(intake, transfer, 1.0));
        D2IntakeTransferOut.whileActiveContinuous(new IntakeTransferCombo(intake, transfer, -1.0));

        // transfer
        D2TransferIn.whileActiveContinuous(new StartEndCommand(
                () -> transfer.transfer(1),
                () -> transfer.transfer(0)
        ));
        D2TransferOut.whileActiveContinuous(new StartEndCommand(
                () -> transfer.transfer(-1),
                () -> transfer.transfer(0)
        ));
        // D2SwitchServoLeft.whileActiveOnce(new InstantCommand(() -> transfer.moveToNegativePoint(), transfer));
        // D2SwitchServoRight.whileActiveOnce(new InstantCommand(() -> transfer.moveToPositivePoint(), transfer));
        ServoReset.whileActiveOnce(new InstantCommand(() -> transfer.moveSwitchToZero(), transfer));

        // shooter
        D2ShooterForward.whileActiveContinuous(new StartEndCommand(
                () -> shooter.setVelocity(SHOOTER_DESIRED_RPM),
                () -> shooter.set(0)
        ));

        // shooter speedup and slowdown
        shooterSpeedUp.whileActiveOnce(new InstantCommand(() -> SHOOTER_DESIRED_RPM += 100));
        shooterSpeedDown.whileActiveOnce(new InstantCommand(() -> SHOOTER_DESIRED_RPM -= 100));
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