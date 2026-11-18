package org.firstinspires.ftc.teamcode.utilities.testingOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.hardware.SensorRevColorV3;
import com.seattlesolvers.solverslib.util.TelemetryData;

@TeleOp(name = "Color Sensor Test", group = "Testing")
public class ColorSensor extends CommandOpMode {
    private SensorRevColorV3 colorSensor;
    private final TelemetryData telemetryData = new TelemetryData(telemetry);

    @Override
    public void initialize() {
        colorSensor = new SensorRevColorV3(hardwareMap, "intakeColor");
    }

    @Override
    public void run() {
        super.run();

        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();
        int alpha = colorSensor.alpha();

        telemetryData.addData("Red", red);
        telemetryData.addData("Green", green);
        telemetryData.addData("Blue", blue);
        telemetryData.addData("Alpha", alpha);

        String detected;

        if (red < 100 && green < 100 && blue < 100) {
            detected = "Nothing";
        } else if (red > 300 && green > 800 && blue > 300) {
            detected = "Pollen";
        } else if (red > 150 && green > 300 && blue > 800) {
            detected = "Blue Nectar";
        } else if (red > 800 && green > 300 && blue > 150) {
            detected = "Red Nectar";
        } else {
            detected = "Unknown";
        }

        telemetryData.addData("Detected", detected);
        telemetryData.update();
    }
}
