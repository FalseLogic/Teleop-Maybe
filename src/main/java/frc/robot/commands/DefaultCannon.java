package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class DefaultCannon extends CommandBase {

    private final Cannon cannon;

    private final BooleanSupplier reverse;

    public DefaultCannon(BooleanSupplier reverse, Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
        
        this.reverse = reverse;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        cannon.shoot(false);
        if(reverse.getAsBoolean()) {
            cannon.setFeeder(1);
        }
        else if(cannon.getShooterSensor()) {
            if(!cannon.getIntakeSensor()) {
                cannon.setFeeder(-1);
            }
            else {
                cannon.setFeeder(0);
            }
        }
        else {
            cannon.setFeeder(0);
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