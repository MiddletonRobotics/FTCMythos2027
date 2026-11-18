package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Deflower extends SubsystemBase {
    private final double DEFLOWER_ANGLE = 0;
    private final double BACK_ANGLE = 90.0;

    private final ServoEx leftDeflower;
    private final ServoEx rightDeflower;

    private final Telemetry telemetry;

    public Deflower(HardwareMap hMap, Telemetry telemetry) {
        leftDeflower = new ServoEx(hMap, "leftDeflower", 0, 300);
        rightDeflower = new ServoEx(hMap, "rightDeflower", 0, 300);

        this.telemetry = telemetry;
    }

    public void periodic() {
        String flowerState = "";
        int cleanState = getFlowerClean();
        if (cleanState == 1) {
            flowerState = "Deflower Retracted";
        } else if (cleanState == 2) {
            flowerState = "Deflower Deployed";
        }
        telemetry.addData("Deflower State", flowerState);
    }

    public void deflower() {
        leftDeflower.set(DEFLOWER_ANGLE);
        rightDeflower.set(DEFLOWER_ANGLE);
    }

    public void back() {
        leftDeflower.set(BACK_ANGLE);
        rightDeflower.set(BACK_ANGLE);
    }

    // one is for retracted
    // two is for deployed
    // three is for anything else
    public int getFlowerClean() {
        if (leftDeflower.get() == BACK_ANGLE && rightDeflower.get() == BACK_ANGLE) {
            return 1;
        } else if (leftDeflower.get() == DEFLOWER_ANGLE && rightDeflower.get() == DEFLOWER_ANGLE) {
            return 2;
        } else {
            return 3;
        }
    }
}
