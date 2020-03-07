package frc.robot.defaultcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlashyLights;

public class DefaultFlashyLights extends CommandBase {

    private final FlashyLights lights;

    private final BooleanSupplier full, seesTarget, onTarget, disco;

    private int allianceR, allianceG, allianceB;

    public DefaultFlashyLights(BooleanSupplier full, BooleanSupplier seesTarget, BooleanSupplier onTarget, BooleanSupplier disco, FlashyLights lights) {
        this.lights = lights;
        addRequirements(lights);

        this.full = full;
        this.seesTarget = seesTarget;
        this.onTarget = onTarget;
        this.disco = disco;
    }

    @Override
    public void initialize() {
        switch(DriverStation.getInstance().getAlliance()) {
            case Blue:
                allianceR = 0; allianceG = 0; allianceB = 200;
                break;
            case Red:
                allianceR = 200; allianceG = 0; allianceB = 0;
                break;
            default:
                allianceR = 119; allianceG = 65; allianceB = 108;
                break;
        }
    }

    @Override
    public void execute() {

        /*if(DriverStation.getInstance().getMatchTime() < 35 && DriverStation.getInstance().isOperatorControl()) {
            if(DriverStation.getInstance().getMatchTime() < 12) {
                lights.rainbow();
            }
            else {
                lights.flash(255, 255, 255, 20);
            }
        }*/
        if(disco.getAsBoolean()) {
            lights.rainbow();
        }
        else if(onTarget.getAsBoolean()) {
            lights.setAllColor(0, 200, 0);
        }
        else if(seesTarget.getAsBoolean()) {
            lights.flash(0, 200, 0, 40);
        }
        else if(full.getAsBoolean()) {
            lights.setAllColor(255, 255, 0);
        }
        else {
            lights.setAllColor(allianceR, allianceG, allianceB);
        }

        lights.setData();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}