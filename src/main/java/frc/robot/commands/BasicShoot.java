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
        double speed = -.92;
        cannon.pidShootPlus(0.7 * speed, speed);
        if(cannon.getBottomVelocity() < -4100 * Math.abs(speed) && cannon.getBottomVelocity() > -4300 * Math.abs(speed)) {
            cannon.setFeeder(-.5);
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