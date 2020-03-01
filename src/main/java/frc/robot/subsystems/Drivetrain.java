package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Limelight;

public class Drivetrain extends SubsystemBase {
	
	private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
	private final SpeedControllerGroup left, right;
	private final DifferentialDrive drive;

	private final AHRS ahrs;
	private final Limelight limelight;

	public Drivetrain() {
		frontLeft = new CANSparkMax(Constants.FRONT_LEFT_ADDRESS, MotorType.kBrushless);
		frontRight = new CANSparkMax(Constants.FRONT_RIGHT_ADDRESS, MotorType.kBrushless);
		backLeft = new CANSparkMax(Constants.BACK_LEFT_ADDRESS, MotorType.kBrushless);
		backRight = new CANSparkMax(Constants.BACK_RIGHT_ADDRESS, MotorType.kBrushless);
		
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

	public void arcadeDrive(double speed, double rotate, boolean squareInputs) {
		drive.arcadeDrive(speed, rotate, squareInputs);
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

	@Override
	public void periodic() {
	//	System.out.println("L: " + getEncoderLeft() + " R: " + getEncoderRight());
		SmartDashboard.putBoolean("Limelight Valid Target", limelight.getValidTarget());
		SmartDashboard.putNumber("Limelight Area", limelight.getArea());
		SmartDashboard.putNumber("Limelight X", limelight.getX());
		SmartDashboard.putNumber("Limelight Y", limelight.getY());
		SmartDashboard.putNumber("Limelight Vertical", limelight.getVertical());
		SmartDashboard.putNumber("Limelight Horizontal", limelight.getHorizontal());
	}
}