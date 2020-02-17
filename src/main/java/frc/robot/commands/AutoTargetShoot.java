package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

public class AutoTargetShoot extends CommandBase {

    private final Drivetrain drivetrain;
    private final Anglers anglers;
    private final Cannon cannon;

    private final Limelight limelight;

    public AutoTargetShoot(Drivetrain drivetrain, Anglers anglers, Cannon cannon) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
        this.anglers = anglers;
        addRequirements(anglers);
        this.cannon = cannon;
        addRequirements(cannon);

        limelight = drivetrain.getLimelight();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {
            double turn = limelight.getX() * Constants.AUTO_TARGET_TURN_KP;
            drivetrain.arcadeDrive(0, turn);

            if(limelight.getY() > 5) {//some deadzone in middle
                anglers.setDart(0); //move up
            }
            else if(limelight.getY() < -5) {
                anglers.setDart(0);//move down
            }
            else {
                anglers.setDart(0);//zero
            }

            if(Math.abs(limelight.getX()) <= 5 && Math.abs(limelight.getY()) <= 5) {//if within thresholds
                //shoot
                cannon.shoot(true);
            }
        }
        else {
            //no target seen
            //maybe tell dashboard something?
            drivetrain.arcadeDrive(0, 0);
            anglers.setDart(0);
            cannon.shoot(false);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

