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
            if(Math.abs(limelight.getX() - 1.35) > 4) {
                turn = Math.copySign(.319, limelight.getX() - 1.5);
            }
            else if(Math.abs(limelight.getX() - 1.35) > .45) {
                turn = Math.copySign(.307, limelight.getX() - 1.5);
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
