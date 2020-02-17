package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Limelight;

public class Drivetrain extends SubsystemBase {
	
	private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
	private final SpeedControllerGroup left, right;
	private final DifferentialDrive drive;

	private final AHRS ahrs;
	private final Limelight limelight;

	public Drivetrain() {
		frontLeft = new CANSparkMax(1, MotorType.kBrushless);
		frontRight = new CANSparkMax(2, MotorType.kBrushless);
		backLeft = new CANSparkMax(3, MotorType.kBrushless);
		backRight = new CANSparkMax(4, MotorType.kBrushless);
		
		frontLeft.restoreFactoryDefaults();
		frontRight.restoreFactoryDefaults();
		backLeft.restoreFactoryDefaults();
		backRight.restoreFactoryDefaults();

		frontLeft.setIdleMode(IdleMode.kBrake);
		frontRight.setIdleMode(IdleMode.kBrake);
		backLeft.setIdleMode(IdleMode.kBrake);
		backRight.setIdleMode(IdleMode.kBrake);

		left = new SpeedControllerGroup(frontLeft, backLeft);
		right = new SpeedControllerGroup(frontRight, backRight);

		drive = new DifferentialDrive(left, right);

		ahrs = new AHRS(SPI.Port.kMXP);

		limelight = new Limelight();

		resetEncoders();
	}

	@Override
	public void periodic() {
	//	System.out.println("L: " + getEncoderLeft() + " R: " + getEncoderRight());
	}

	public void arcadeDrive(double speed, double rotate) {
		drive.arcadeDrive(speed, rotate);
	}

	public double getEncoderLeft() {
		return frontLeft.getEncoder().getPosition() + backLeft.getEncoder().getPosition();
	}
	public double getEncoderRight() {
		return frontRight.getEncoder().getPosition() + backRight.getEncoder().getPosition();
	}
	public double getEncoderAverage() {
		return (getEncoderLeft() + getEncoderRight()) / 2;
	}
	public void resetEncoders() {
		frontLeft.getEncoder().setPosition(0);
		frontRight.getEncoder().setPosition(0);
		backLeft.getEncoder().setPosition(0);
		backRight.getEncoder().setPosition(0);
	}

	double gyroOffset = 0;
	public double getAngle() {
		return ahrs.getAngle() - gyroOffset;
	}
	public void resetAngle() {
		gyroOffset = ahrs.getAngle();
	}

	public Limelight getLimelight() {
		return limelight;
	}
}