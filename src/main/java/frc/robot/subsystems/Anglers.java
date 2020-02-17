package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Anglers extends SubsystemBase {

	private final CANSparkMax dart;
	private final WPI_TalonSRX lead;

	private final DigitalInput topLim, botLim, leadLim;

	public Anglers() {
		dart = new CANSparkMax(Constants.DART_ADDRESS, MotorType.kBrushless);
		dart.restoreFactoryDefaults();

		lead = new WPI_TalonSRX(Constants.LEAD_ADDRESS);

		topLim = new DigitalInput(0);
		botLim = new DigitalInput(1);
		leadLim = new DigitalInput(2);
	}

	public void setDart(double pow) {
		dart.set(pow);
	}
	public void setLead(double pow){
		lead.set(pow);
	}

	public boolean getTopLimit() {
		return topLim.get();
	}
	public boolean getBottomLimit() {
		return botLim.get();
	}
	public boolean getLeadLimit() {
		return leadLim.get();
	}

	@Override
	public void periodic() {
	}
}
