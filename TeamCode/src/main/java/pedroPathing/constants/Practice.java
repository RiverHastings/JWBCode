import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

// Level 1.1: Straight Line
private final Pose startPose = new Pose(0, 0, Math.toRadians(0));
private final Pose endPose = new Pose(30, 0, Math.toRadians(0));
private int pathState = 0;
private Timer pathTimer;

private Path forwardLine;

public void buildPaths(){
  forwardLine = new Path(new BezierLine(new Point(startPose), new Point(endPose))); 
  forwardLine.setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading());

  autoChain = new PathChain().addPath(forwardLine);
}

public void autonomousPathUpdate() {
    switch (pathState) {
        case 0:
            follower.followPath(autoChain);
            break;
    }
}

public void setPathState(int pState) {
    pathState = pState;
    pathTimer.resetTimer();
}

@Override
public void init() {
    telemetry.setAutoClear(false);
    pathTimer = new Timer();
    Constants.setConstants(FConstants.class, LConstants.class);
    follower = new Follower(hardwareMap);
    follower.setStartingPose(startPose);
    buildPaths();
}

@Override
public void loop() {
    follower.update();
    autonomousPathUpdate();
    telemetry.addData("Path State", pathState);
    telemetry.addData("Position", follower.getPose().toString());
    telemetry.update();
}
