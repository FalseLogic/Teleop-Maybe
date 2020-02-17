package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final DoubleSupplier forward, rotation;
    private final BooleanSupplier invert;

    public DefaultDrive(DoubleSupplier forward, DoubleSupplier rotation, BooleanSupplier invert, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        this.forward = forward;
        this.rotation = rotation;
        this.invert = invert;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double speed = forward.getAsDouble() * .5 * (invert.getAsBoolean() ? 1 : -1);
        double rotate = rotation.getAsDouble() * .5;

        drivetrain.arcadeDrive(speed, rotate);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}