package org.firstinspires.ftc.teamcode.OpModes.Auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;
import com.seattlesolvers.solverslib.util.TelemetryData;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Leave Auto", group = "Auto")
public class LeaveAuto extends CommandOpMode {
    private Follower follower;
    private final TelemetryData telemetryData = new TelemetryData(telemetry);

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // Start pose and leave pose (moving forward)
    private final Pose startPose = poseFactory.of(0, 0, 0);
    private final Pose leavePose = poseFactory.of(24, 0, 0);

    private Path leavePath;

    @Override
    public void initialize() {
        super.reset();

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose);

        leavePath = line(startPose, leavePose)
                .linear(startPose, leavePose);

        schedule(new SequentialCommandGroup(
                new FollowPathCommand(follower, leavePath)
        ));
    }

    @Override
    public void run() {
        super.run();

        follower.update();

        telemetryData.addData("X", follower.pose().x());
        telemetryData.addData("Y", follower.pose().y());
        telemetryData.addData("Heading", follower.pose().heading());
        telemetryData.update();
    }
}
