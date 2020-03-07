package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;
import frc.robot.util.Limelight;

public class LimelightAngle extends CommandBase {

    public static boolean onTarget;

    private final Anglers anglers;

    private final Limelight limelight;

    private boolean finished;

    public LimelightAngle(Limelight limelight, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.limelight = limelight;
    }

    @Override
    public void initialize() {
        finished = false;
        onTarget = false;
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            double heightAdjustDegrees = (limelight.getVertical() / 2) * (59.6 / 320) / 2;
            if(limelight.getY() + heightAdjustDegrees - .5 < -.5) {
                if(anglers.getDartBottomLimit()) {
                    anglers.setDartSafely(-.32);
                }
            }
            else if(limelight.getY() + heightAdjustDegrees - .5 > .5) {
                if(anglers.getDartTopLimit()) {
                    anglers.setDartSafely(.32);
                }
            }
            else {
                anglers.setDartSafely(0);
                finished = true;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        anglers.setDartSafely(0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
