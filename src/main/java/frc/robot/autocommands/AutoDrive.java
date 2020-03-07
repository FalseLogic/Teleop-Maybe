package frc.robot.autocommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final double power, targetDistance;
    private double startEncoder;

    public AutoDrive(double targetDistance, double power, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        this.power = power;
        this.targetDistance = targetDistance;
    }

    @Override
    public void initialize() {
        startEncoder = drivetrain.getEncoderAverage();
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(power, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if(Math.abs(drivetrain.getEncoderAverage() - startEncoder) >= targetDistance) {
            return true;
        }
        return false;
    }
}
