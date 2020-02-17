package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
	
	private final WPI_TalonSRX arm, intake;

	public Intake() {
		arm = new WPI_TalonSRX(Constants.INTAKE_ARM_ADDRESS);
		intake = new WPI_TalonSRX(Constants.INTAKE_SUCKER_ADDRESS);
	}

	public void setArm(double power) {
		arm.set(power);
	}
	public void setIntake(double power) {
		intake.set(power);
	}

	@Override
	public void periodic() {

	}

}