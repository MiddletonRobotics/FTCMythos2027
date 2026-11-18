package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.Depollinator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.utilities.PoseStorage;

@TeleOp(name = "BlueTeleOp", group = "TeleOp")
public class BlueTeleOp extends CommandOpMode {
    @Override
    public void initialize() {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Intake intake = new Intake(hardwareMap, telemetry);
        Depollinator depollinator = new Depollinator(hardwareMap, telemetry);
        Shooter shooter = new Shooter(hardwareMap, telemetry);
        Transfer transfer = new Transfer(hardwareMap, intake, shooter, telemetry, false);

        if (PoseStorage.currentPose != null) {
            drivetrain.getFollower().setPose(PoseStorage.currentPose);
        } else {
            drivetrain.getFollower().setPose(new Pose(0, 0, Math.toRadians(180)));
        }

        GamepadEx driver1 = new GamepadEx(gamepad1);
        GamepadEx driver2 = new GamepadEx(gamepad2);

        TeleOpBindings bindings = new TeleOpBindings(driver1, driver2, drivetrain, intake, depollinator, transfer, shooter);

        bindings.controlMap();
        bindings.configureDefaultCommands(Math.PI);
    }

    @Override
    public void run() {
        super.run();
        telemetry.update();
    }
}
