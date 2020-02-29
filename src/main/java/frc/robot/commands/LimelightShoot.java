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

            double turn = Math.copySign(.20, limelight.getX() - 1);
            drivetrain.arcadeDrive(0, Math.abs(limelight.getX() - 1) > .5 ? turn : 0);

            if(limelight.getArea() > 2) {
                shoot(.021 * limelight.getArea() - .85);
            }
            else {
                //long shot here
    //            shoot(-.9);
            }
        }
    }

    private void shoot(double speed) {
        cannon.pidShootPlus(0.7 * speed, speed);
        if(cannon.getBottomVelocity() < -4100 * Math.abs(speed) && cannon.getBottomVelocity() > -4300 * Math.abs(speed)) {
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
