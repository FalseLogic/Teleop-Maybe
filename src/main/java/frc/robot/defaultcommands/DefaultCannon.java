package frc.robot.defaultcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class DefaultCannon extends CommandBase {

    public static boolean isFull;

    private final Cannon cannon;

    private final BooleanSupplier reverse, climb, unclimb;

    public DefaultCannon(BooleanSupplier reverse, BooleanSupplier climb, BooleanSupplier unclimb, Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
        
        this.reverse = reverse;
        this.climb = climb;
        this.unclimb = unclimb;

        isFull = false;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        cannon.shoot(false);
        if(climb.getAsBoolean()) {
            cannon.setClimber(-1);
        }
        else if(unclimb.getAsBoolean()) {
            cannon.setClimber(1);
        }
        else  {
            cannon.setClimber(0);
            if(reverse.getAsBoolean()) {
                cannon.setFeeder(.6);
                isFull = false;
            }
            else if(cannon.getShooterSensor()) {
                if(!cannon.getIntakeSensor()) {
                    cannon.setFeeder(-.8);
                }
                else {
                    cannon.setFeeder(0);
                }
                isFull = false;
            }
            else {
                isFull = true;
                cannon.setFeeder(0);
            }
        }

    }

    @Override
    public void end(boolean interrupted) {
        cannon.shoot(false);
        cannon.setFeeder(0);
        cannon.setClimber(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}