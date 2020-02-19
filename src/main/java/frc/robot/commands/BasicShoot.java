package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class BasicShoot extends CommandBase {

    private final Cannon cannon;

    public BasicShoot(Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double x = -.8;
        cannon.pidShoot(0.7 * x, x);
        if(cannon.getBottomVelocity() < -3600 * Math.abs(x)) {
            cannon.setFeeder(-1);
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