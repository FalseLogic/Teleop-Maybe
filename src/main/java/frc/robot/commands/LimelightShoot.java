package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

public class LimelightShoot extends CommandBase {

    private final Cannon cannon;
    private final Drivetrain drivetrain;

    private final Limelight limelight;

    public LimelightShoot(Cannon cannon, Drivetrain drivetrain) {
        this.cannon = cannon;
        addRequirements(cannon);

        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            if(limelight.getArea() > 6) {
                shoot(.8);
            }
            else {

            }
        }
    }

    private void shoot(double speed) {
        cannon.pidShoot(0.65 * speed, speed);
        if(cannon.getBottomVelocity() < -3600 * Math.abs(speed)) {
            cannon.setFeeder(-1);
        }
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
