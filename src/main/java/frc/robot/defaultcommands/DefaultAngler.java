package frc.robot.defaultcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;

public class DefaultAngler extends CommandBase {

    private final Anglers anglers;
    private final BooleanSupplier up, down, reset;

    public DefaultAngler(BooleanSupplier up, BooleanSupplier down, BooleanSupplier reset, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.up = up;
        this.down = down;
        this.reset = reset;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(reset.getAsBoolean()) {
            anglers.setLeadSafely(-.4);
            if(anglers.getDartPot() < 3.75) {
                anglers.setDartSafely(.4);
            }
            else if(anglers.getDartPot() > 3.79) {
                anglers.setDartSafely(-.4);
            }
            else {
                anglers.setDartSafely(0);
            }
        }
        else if(up.getAsBoolean()) {
            if(!anglers.setDartSafely(.8)) {
                anglers.setLeadSafely(.8);
            }
        }
        else if(down.getAsBoolean()) {
            if(!anglers.setLeadSafely(-.8)) {
                anglers.setDartSafely(-.8);
            }
        }
        else {
            anglers.setDartSafely(0);
            anglers.setLeadSafely(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        anglers.setDartSafely(0);
        anglers.setLeadSafely(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}