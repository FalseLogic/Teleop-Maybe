package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

public class LimelightDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final Limelight limelight;

    private boolean finished;


    public LimelightDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            double turn;
            if(Math.abs(limelight.getX() - 2) > 3) {
                turn = Math.copySign(.34, limelight.getX() - 1);
            }
            else if(Math.abs(limelight.getX() - 2) > 1) {
                turn = Math.copySign(.32, limelight.getX() - 1);
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
