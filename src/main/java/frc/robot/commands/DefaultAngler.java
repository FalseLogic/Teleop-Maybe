package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;

public class DefaultAngler extends CommandBase {

    private final Anglers anglers;
    private final BooleanSupplier dartUp, dartDown, leadUp, leadDown;

    public DefaultAngler(BooleanSupplier dartUp, BooleanSupplier dartDown, BooleanSupplier leadUp, BooleanSupplier leadDown, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.dartUp = dartUp;
        this.dartDown = dartDown;
        this.leadUp = leadUp;
        this.leadDown = leadDown;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(dartUp.getAsBoolean()) {
            anglers.setDartSafely(.3);
        }
        else if(dartDown.getAsBoolean()) {
            anglers.setDartSafely(-.3);
        }
        else {
            anglers.setDartSafely(0);
        }

        if(leadUp.getAsBoolean()) {
            anglers.setLeadSafely(.5);
        }
        else if(leadDown.getAsBoolean()) {
            anglers.setLeadSafely(-.5);
        }
        else {
            anglers.setLead(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        anglers.setDartSafely(0);
        anglers.setLead(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}