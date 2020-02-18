package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

public class LimelightDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final Limelight limelight;

    public LimelightDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double turn = .1 * limelight.getX();
        if(Math.abs(turn) > .345) {
            turn = Math.copySign(.345, turn);
        }
        if(Math.abs(limelight.getX()) < 1) {
            turn = 0;
        }
        drivetrain.arcadeDrive(0, turn);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
