package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Subsystems.Mythos;
import org.firstinspires.ftc.teamcode.TeleOpBindings;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends CommandOpMode {
    @Override
    public void initialize() {
        Mythos robot = new Mythos(hardwareMap, telemetry);

        robot.drivetrain.getFollower().setPose(new Pose(0, 0, Math.toRadians(0)));

        GamepadEx driver1 = new GamepadEx(gamepad1);
        GamepadEx driver2 = new GamepadEx(gamepad2);

        TeleOpBindings bindings = new TeleOpBindings(driver1, driver2, robot);

        bindings.controlMap();
        bindings.configureDefaultCommands();
    }

    @Override
    public void run() {
        super.run();
        telemetry.update();
    }
}
