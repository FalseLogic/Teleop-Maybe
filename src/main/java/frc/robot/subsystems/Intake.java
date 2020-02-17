package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
	
	private final WPI_TalonSRX intakeArm, sucker;

	public Intake() {
		intakeArm = new WPI_TalonSRX(Constants.INTAKE_ARM_ADDRESS);
		sucker = new WPI_TalonSRX(Constants.INTAKE_SUCKER_ADDRESS);
	}

	public void setArm(double power) {
		intakeArm.set(power);
	}

	public void setSuck(double power) {
		sucker.set(power);
	}

	public void intakeComs(double suckPow, double armPow) {
		setSuck(suckPow);
		setArm(armPow);
	}

	@Override
	public void periodic() {

	}

}
