package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DefaultIntake extends CommandBase {

    private final Intake intake;

    private final BooleanSupplier suck, spit, armUp, armDown;

    public DefaultIntake(BooleanSupplier suck, BooleanSupplier spit, BooleanSupplier armUp, BooleanSupplier armDown, Intake intake) {
        this.intake = intake;
        addRequirements(intake);

        this.suck = suck;
        this.spit = spit;
        this.armUp = armUp;
        this.armDown = armDown;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(suck.getAsBoolean()) {
            intake.setIntake(-.75);
            intake.setArm(.35);
        }
        else if(spit.getAsBoolean()) {
            intake.setIntake(.5);
            intake.setArm(0);
        }
        else {
            intake.setIntake(0);
            if(armUp.getAsBoolean()) {
                intake.setArm(-1);
            }
            else if(armDown.getAsBoolean()) {
                intake.setArm(1);
            }
            else {
                intake.setArm(0);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntake(0);
        intake.setArm(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}