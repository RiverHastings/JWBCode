package pedroPathing.constants;

// File: AutonomousOpMode.java
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Point;


@Autonomous(name = "Pedro Auto", group = "Autonomous")
public class AutonomousOpMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Follower follower;

    @Override
    public void runOpMode() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);

        waitForStart();

        // Inside runOpMode(), numerics can be updated later
        Point start = new Point(0, 0);
        Point control = new Point(10, 0);
        Point end = new Point(20, 20);

        BezierCurve curve = new BezierCurve(start, control, end);
        Path examplePath = new Path(curve);

        performPathWithTime(follower, examplePath, 5.0, "intake");
    }

    public void performPathWithTime(Follower follower, Path path, double timeLimit, String subsystemMode) {
        runtime.reset();
        follower.followPath(path);

        while (opModeIsActive() && runtime.seconds() < timeLimit) {
            double time = runtime.seconds();

            // Placeholder example: replace with actual condition/subsystem logic
            if ("intake".equals(subsystemMode)) {
                if (time > 1.0 && time < 3.0) {
                    // intake.setPower(1.0);
                } else {
                    // intake.setPower(0.0);
                }
            }

            follower.update();
            telemetry.addData("Time", time);
            telemetry.update();
        }

        // Stop subsystems if needed
        if ("intake".equals(subsystemMode)) {
            // intake.setPower(0.0);
        }

        telemetry.addData("Finished Path", "Time Limit Reached");
        telemetry.update();
    }
} // Conditionality Change Later