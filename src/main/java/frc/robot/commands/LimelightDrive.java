package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

public class LimelightDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final Limelight limelight;

    boolean finished;

    public LimelightDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
        finished = false;
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            double turn;
            if(Math.abs(limelight.getX()) > 2) {
                turn = Math.copySign(.33, limelight.getX());
            }
            else {
                turn = 0;
                finished = true;
            }
            drivetrain.arcadeDrive(0, turn);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
