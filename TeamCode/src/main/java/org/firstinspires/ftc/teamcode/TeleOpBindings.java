package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.subsystems.Mythos;

public class TeleOpBindings {
    private final GamepadEx driver1;
    private final Mythos robot;

    private final Button intakeIn;
    private final Button intakeOut;

    public TeleOpBindings(GamepadEx driver1, Mythos robot) {
        this.driver1 = driver1;
        this.robot = robot;

        intakeIn = new GamepadButton(driver1, GamepadKeys.Button.A);
        intakeOut = new GamepadButton(driver1, GamepadKeys.Button.B);
    }

    public void controlMap() {
        // intake
        intakeIn.whenActive(() -> robot.intake.intake(1))
                .whenInactive(() -> robot.intake.intake(0));
        intakeOut.whenActive(() -> robot.intake.intake(-1))
                .whenInactive(() -> robot.intake.intake(0));

        // transfer

        //shooter
    }

    public void configureDefaultCommands() {
        //drivetrain
        robot.drivetrain.setDefaultCommand(new DrivetrainController(
                robot.drivetrain,
                () -> -driver1.getLeftY(),
                () -> -driver1.getRightX(),
                () -> -driver1.getLeftX()
        ));
    }
}