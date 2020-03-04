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

	private final DigitalInput dartTopLimit, dartBottomLimit, leadFrontLimit, leadBackLimit;
	private final AnalogInput dartPot;

	public Anglers() {
		dart = new CANSparkMax(Constants.DART_ADDRESS, MotorType.kBrushless);
		dart.restoreFactoryDefaults();

		lead = new WPI_TalonSRX(Constants.LEAD_ADDRESS);

		dartTopLimit = new DigitalInput(Constants.DART_TOP_LIMIT_ADDRESS);
		dartBottomLimit = new DigitalInput(Constants.DART_BOTTOM_LIMIT_ADDRESS);
		leadFrontLimit = new DigitalInput(Constants.LEAD_FRONT_LIMIT_ADDRESS);
		leadBackLimit = new DigitalInput(Constants.LEAD_BACK_LIMIT_ADDRESS);

		dartPot = new AnalogInput(Constants.DART_POTENTIOMETER_ADDRESS);
	}

	private void setDart(double pow) {
		dart.set(pow);
	}
	public boolean setDartSafely(double pow) {
		if((pow > 0 && getDartTopLimit()) || (pow < 0 && getDartBottomLimit())) {
			setDart(pow);
			return true;
		}
		else {
			setDart(0);
			return false;
		}
	}
	public void setLead(double pow) {
		lead.set(pow);
	}
	public boolean setLeadSafely(double pow) {
		if((pow > 0 && !getLeadBackLimit()) || (pow < 0 && !getLeadFrontLimit())) {
			setLead(pow);
			return true;
		}
		else {
			setLead(0);
			return false;
		}
	}

	public void getInsidePerimeter() {
	//	setLeadSafely(-.5);
	}

	public boolean getDartTopLimit() {
		return dartTopLimit.get();
	}
	public boolean getDartBottomLimit() {
		return dartBottomLimit.get();
	}
	public boolean getLeadFrontLimit() {
		return leadFrontLimit.get();
	}
	public boolean getLeadBackLimit() {
		return leadBackLimit.get();
	}

	public double getDartPot() {
		return dartPot.getAverageVoltage();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Dart Potentiometer", dartPot.getAverageVoltage());
	}
}
