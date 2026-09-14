package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Commands.DrivetrainController;
import org.firstinspires.ftc.teamcode.subsystems.Mythos;

public class TeleOpBindings {
    private final GamepadEx driver1;
    private final Mythos robot;

    public TeleOpBindings(GamepadEx driver1, Mythos robot) {
        this.driver1 = driver1;
        this.robot = robot;
    }

    public void controlMap() {


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