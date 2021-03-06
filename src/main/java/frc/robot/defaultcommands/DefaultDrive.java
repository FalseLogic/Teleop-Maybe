package frc.robot.defaultcommands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final DoubleSupplier forward, rotation;
    private final BooleanSupplier invert;

    private int direction  = -1;

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

        if(invert.getAsBoolean()) {
            direction *= -1;
        }

        double speed = forward.getAsDouble() * direction;
        double rotate = rotation.getAsDouble();

        speed = Math.copySign(Math.pow(speed, 3) * .725, speed);
        rotate = Math.copySign(Math.pow(rotate, 2) * .55, rotate);

        drivetrain.arcadeDrive(speed, rotate, false);
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