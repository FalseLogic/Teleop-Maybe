package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DefaultIntake extends CommandBase {

    private final Intake intake;

    private final BooleanSupplier suck;

    public DefaultIntake(BooleanSupplier suck, Intake intake) {
        this.intake = intake;
        addRequirements(intake);

        this.suck = suck;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(suck.getAsBoolean()) {
            intake.setIntake(-.7);
        }
        else {
            intake.setIntake(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntake(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}