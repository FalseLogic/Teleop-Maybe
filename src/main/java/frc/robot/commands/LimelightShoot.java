package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;
import frc.robot.util.Limelight;

public class LimelightShoot extends CommandBase {

    private final Cannon cannon;

    private final Limelight limelight;

    public LimelightShoot(Cannon cannon, Limelight limelight) {
        this.cannon = cannon;
        addRequirements(cannon);

        this.limelight = limelight;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            shoot(.0307 * limelight.getArea() - .895);
        }
    }

    private void shoot(double speed) {
        cannon.pidShootPlus(0.7 * speed, speed);
        if(cannon.getBottomVelocity() < -4000 * Math.abs(speed) && cannon.getBottomVelocity() > -4400 * Math.abs(speed)) {
            cannon.setFeeder(-.6);
        }
    }

    @Override
    public void end(boolean interrupted) {
        cannon.shoot(false);
        cannon.setFeeder(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
