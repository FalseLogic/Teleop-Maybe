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
            if(limelight.getArea() > 6.5) {
                if(limelight.getY() + 3.5 < -.45) {
                    if(anglers.getBottomLimit()) {
                        anglers.setDart(-.35);
                    }
                }
                else if(limelight.getY() + 3.5 > .45) {
                    if(anglers.getTopLimit()) {
                        anglers.setDart(.35);
                    }
                }
                else {
                    anglers.setDart(0);
                    finished = true;
                }
            }
            else {
                finished = anglers.setDartPosition(.227);
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
