package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class DefaultCannon extends CommandBase {

    private final Cannon cannon;

    private final BooleanSupplier shoot, reverse;

    private boolean sensorWasTriggered;
    private Timer indexTimer;

    public DefaultCannon(BooleanSupplier shoot, BooleanSupplier reverse, Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
        
        this.shoot = shoot;
        this.reverse = reverse;

        indexTimer = new Timer();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        if(shoot.getAsBoolean()) {
            cannon.pidShoot(-.8, -1);
            cannon.setFeeder(-.75);
        }
        else {
            cannon.shoot(false);
            if(reverse.getAsBoolean()) {
                cannon.setFeeder(1);
            }
            else {
                if(!cannon.getFrontSensor()) {
                    cannon.setFeeder(-1);
                    indexTimer.reset();
                    indexTimer.start();
                    sensorWasTriggered = true;
                }
                else if(sensorWasTriggered) {
                    if(indexTimer.get() > .25) {
                        indexTimer.stop();
                        indexTimer.reset();
                        sensorWasTriggered = false;
                        cannon.setFeeder(0);
                    }
                }
                else {
                    cannon.setFeeder(0);
                }
            }
        }

    }

    @Override
    public void end(boolean interrupted) {
        cannon.setFeeder(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}