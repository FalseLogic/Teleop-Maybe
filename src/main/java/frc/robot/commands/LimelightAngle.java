package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;
import frc.robot.util.Limelight;

public class LimelightAngle extends CommandBase {

    private final Anglers anglers;

    private final Limelight limelight;

    public LimelightAngle(Limelight limelight, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.limelight = limelight;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
