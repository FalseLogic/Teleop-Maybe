package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;
import frc.robot.util.Limelight;

public class LimelightAngle extends CommandBase {

    private final Anglers anglers;

    private final Limelight limelight;

    boolean finished = false;

    public LimelightAngle(Limelight limelight, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.limelight = limelight;
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            if(limelight.getArea() > 6) {
                if(limelight.getY() + 6 < -.25) {
                    if(anglers.getBottomLimit()) {
                        anglers.setDart(-.4);
                    }
                }
                else if(limelight.getY() + 6 > .25) {
                    if(anglers.getTopLimit()) {
                        anglers.setDart(.4);
                    }
                }
                else {
                    anglers.setDart(0);
                    finished = true;
                }
            }
            else {
                finished = anglers.setDartPosition(.22);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        anglers.setDart(0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
