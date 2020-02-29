package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class TestCommand extends CommandBase {

    private final BooleanSupplier a;

    private final Cannon cannon;

    public TestCommand(BooleanSupplier a, Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);

        this.a = a;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(a.getAsBoolean()) {
            cannon.setClimber(1);
        }
        else {
            cannon.setClimber(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        cannon.shoot(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}