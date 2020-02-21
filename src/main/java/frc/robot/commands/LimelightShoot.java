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

            double turn = Math.copySign(.23, limelight.getX());
            drivetrain.arcadeDrive(0, Math.abs(limelight.getX()) > .5 ? turn : 0);

            if(limelight.getArea() > 6) {
                shoot(-.8);
            }
            else {
                shoot(-.9);
            }
        }
    }

    private void shoot(double speed) {
        cannon.pidShoot(0.7 * speed, speed);
        if(cannon.getBottomVelocity() < -4000 * Math.abs(speed)) {
            cannon.setFeeder(-1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
        cannon.shoot(false);
        cannon.setFeeder(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
