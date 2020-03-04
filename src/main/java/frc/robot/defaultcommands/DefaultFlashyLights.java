package frc.robot.defaultcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlashyLights;

public class DefaultFlashyLights extends CommandBase {

    private final FlashyLights lights;

    private final BooleanSupplier full, seesTarget, onTarget, disco;

    private Color allianceColor;

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
                allianceColor = Color.kFirstBlue;
                break;
            case Red:
                allianceColor = Color.kFirstRed;
                break;
            default:
                allianceColor = Color.kDarkViolet;
                break;
        }
    }

    @Override
    public void execute() {
        if(disco.getAsBoolean()) {
            lights.rainbow();
        }
        else if(onTarget.getAsBoolean()) {
            lights.setAllColor(Color.kGreen);
        }
        else if(seesTarget.getAsBoolean()) {
            lights.flash(Color.kGreen, 10);
        }
        else if(full.getAsBoolean()) {
            lights.setAllColor(Color.kYellow);
        }
        else {
            lights.setAllColor(allianceColor);
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