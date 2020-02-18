package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Anglers extends SubsystemBase {

	private final CANSparkMax dart;
	private final WPI_TalonSRX lead;

	private final DigitalInput topLim, botLim, leadLim;
	private final AnalogInput dartPot;

	public Anglers() {
		dart = new CANSparkMax(Constants.DART_ADDRESS, MotorType.kBrushless);
		dart.restoreFactoryDefaults();

		lead = new WPI_TalonSRX(Constants.LEAD_ADDRESS);

		topLim = new DigitalInput(0);
		botLim = new DigitalInput(1);
		leadLim = new DigitalInput(2);

		dartPot = new AnalogInput(3);
	}

	public void setDart(double pow) {
		dart.set(pow);
	}
	public void setLead(double pow) {
		lead.set(pow);
	}
	public void setDartPosition(double target) {
		double realTarget = target * 4 + 0.9;
		if(realTarget > getDartPot() + .05 && getTopLimit()) {
			setDart(.8);
		}
		else if(realTarget < getDartPot() - .05 && getBottomLimit()) {
			setDart(-.8);
		}
		else {
			setDart(0);
		}
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
	public double getDartPot() {
		return dartPot.getAverageVoltage();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Dart Potentiometer", dartPot.getAverageVoltage());
	}
}
