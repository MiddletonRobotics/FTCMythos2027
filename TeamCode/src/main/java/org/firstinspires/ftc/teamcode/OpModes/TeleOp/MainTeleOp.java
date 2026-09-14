package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.TeleOpBindings;
import org.firstinspires.ftc.teamcode.subsystems.Mythos;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends CommandOpMode {
    @Override
    public void initialize() {
        Mythos robot = new Mythos(hardwareMap, telemetry);

        GamepadEx driver1 = new GamepadEx(gamepad1);

        TeleOpBindings bindings = new TeleOpBindings(driver1, robot);

        bindings.controlMap();
        bindings.configureDefaultCommands();
    }

    @Override
    public void run() {
        super.run();
        telemetry.update();
    }
}