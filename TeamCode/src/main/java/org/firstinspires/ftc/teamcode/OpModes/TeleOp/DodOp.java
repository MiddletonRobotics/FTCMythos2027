package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.Subsystems.Mythos;

// This TeleOp goes with the DodOp (c) document
@TeleOp(name = "DodOp")
public class DodOp extends CommandOpMode {
    private GamepadEx driver1;
    private GamepadEx driver2;
    private Mythos robot;

    // intake
    private GamepadButton intakeIn;
    private GamepadButton intakeOut;

    // transfer
    private GamepadButton transferIn;
    private GamepadButton transferOut;

    // shooter
    private GamepadButton shooterForward;
    private GamepadButton shooterBackward;

    @Override
    public void initialize() {
        robot = new Mythos(hardwareMap, telemetry);

        driver1 = new GamepadEx(gamepad1);
        driver2 = new GamepadEx(gamepad2);

        // init button bindings
        // intake
        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.A);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.B);

        // transfer
        transferIn = new GamepadButton(driver1, GamepadKeys.Button.X);
        transferOut = new GamepadButton(driver1, GamepadKeys.Button.Y);

        // shooter
        shooterForward = new GamepadButton(driver1, GamepadKeys.Button.LEFT_STICK_BUTTON);
        shooterBackward = new GamepadButton(driver1, GamepadKeys.Button.RIGHT_STICK_BUTTON);

        controlMapDodOp();
        configureDefaultDodOpCommands();
    }

    public void controlMapDodOp() {
        // intake
        intakeIn.whileActiveContinuous(() -> robot.intake.intake(1));
        intakeOut.whileActiveContinuous(() -> robot.intake.intake(-1));

        // transfer
        transferIn.whileActiveContinuous(() -> robot.transfer.transfer(1));
        transferOut.whileActiveContinuous(() -> robot.transfer.transfer(-1));

        // shooter
        shooterForward.whileActiveContinuous(() -> robot.shooter.set(1));
        shooterBackward.whileActiveContinuous(() -> robot.shooter.set(-1));
    }

    public void configureDefaultDodOpCommands() {
        robot.drivetrain.setDefaultCommand(new DrivetrainController(
                robot.drivetrain,
                () -> driver1.getLeftY(),
                () -> driver1.getRightX(),
                () -> driver1.getLeftX()
        ));
    }

    @Override
    public void run() {
        super.run();
        telemetry.update();
    }
}
