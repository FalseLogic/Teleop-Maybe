package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class LimelightIsOnTarget extends CommandBase {

    public LimelightIsOnTarget() {
    }

    @Override
    public void initialize() {
        RobotContainer.limelightOnTarget = true;
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.limelightOnTarget = false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
