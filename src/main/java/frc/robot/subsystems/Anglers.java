package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Anglers extends SubsystemBase {

	private final CANSparkMax dart;
	private final WPI_TalonSRX lead;

	public Anglers() {
		dart = new CANSparkMax(Constants.DART_ADDRESS, MotorType.kBrushless);
		dart.restoreFactoryDefaults();
		

		lead = new WPI_TalonSRX(Constants.LEAD_ADDRESS);
	}

	public void setDart(double pow) {
		dart.set(pow);
	}

	public void setLead(double pow){
		lead.set(pow);
	}

	public void angleComs(double pow1, double pow2){
		setDart(pow1);
		setLead(pow2);
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
