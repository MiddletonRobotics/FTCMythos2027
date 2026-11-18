package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Depollinator extends SubsystemBase {
    private final double DEFLOWER_ANGLE = 0;
    private final double BACK_ANGLE = 90.0;

    private final ServoEx leftDeflower;
    private final ServoEx rightDeflower;

    private final Telemetry telemetry;

    private boolean isDeployed = false;

    public Depollinator(HardwareMap hMap, Telemetry telemetry) {
        leftDeflower = new ServoEx(hMap, "leftDeflower", 0, 300);
        rightDeflower = new ServoEx(hMap, "rightDeflower", 0, 300);

        depolliateBack();

        this.telemetry = telemetry;
    }

    public void periodic() {
        telemetry.addData("Deflower State", isDeployed ? "Deployed" : "Retracted");
    }

    public void depolliate() {
        leftDeflower.set(BACK_ANGLE);
        rightDeflower.set(DEFLOWER_ANGLE);
        isDeployed = true;
    }

    public void depolliateBack() {
        leftDeflower.set(DEFLOWER_ANGLE);
        rightDeflower.set(BACK_ANGLE);
        isDeployed = false;
    }

    public void toggleDepollinate() {
        isDeployed = !isDeployed;
        if (isDeployed) {
            depolliate();
        } else {
            depolliateBack();
        }
    }
}
