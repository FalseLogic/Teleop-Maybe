package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Cannon extends SubsystemBase {
	
	private final CANSparkMax topShoot, botShoot;
	private final WPI_TalonSRX feeder, climber;

	private final DigitalInput intakeSensor, shooterSensor;

	private int climbEncoderOffset;

	public Cannon() {
		topShoot = new CANSparkMax(Constants.TOP_SHOOTER_ADDRESS, MotorType.kBrushless);
		botShoot = new CANSparkMax(Constants.BOTTOM_SHOOTER_ADDRESS, MotorType.kBrushless);

		topShoot.restoreFactoryDefaults();
		topShoot.setIdleMode(IdleMode.kCoast);
		topShoot.getPIDController().setP(Constants.SHOOTER_VELOCITY_KP);
		topShoot.getPIDController().setFF(Constants.SHOOTER_VELOCITY_KF);
		topShoot.getPIDController().setOutputRange(-1, 1);

		botShoot.restoreFactoryDefaults();
		botShoot.setIdleMode(IdleMode.kCoast);
		botShoot.getPIDController().setP(Constants.SHOOTER_VELOCITY_KP);
		botShoot.getPIDController().setFF(Constants.SHOOTER_VELOCITY_KF);
		botShoot.getPIDController().setOutputRange(-1, 1);

		feeder = new WPI_TalonSRX(Constants.FEEDER_ADDRESS);
		climber = new WPI_TalonSRX(Constants.CLIMBER_ADDRESS);

		feeder.configFactoryDefault();
		feeder.setNeutralMode(NeutralMode.Brake);

		climber.configFactoryDefault();
		climber.setNeutralMode(NeutralMode.Brake);
		climbEncoderOffset =  climber.getSelectedSensorPosition();

		intakeSensor = new DigitalInput(Constants.INTAKE_BALL_SENSOR_ADDRESS);
		shooterSensor = new DigitalInput(Constants.SHOOTER_BALL_SENSOR_ADDRESS);
	}

	public void setTopShoot(double a) {
		topShoot.set(a);
	}
	public void setBottomShoot(double a) {
		botShoot.set(a);
	}
	public void shoot(boolean b){
		if(b) {
			topShoot.set(-.5);
			botShoot.set(-.8);
		}
		else {
			topShoot.set(0);
			botShoot.set(0);
		}
	}
	public void pidShoot(double top, double bot) {
		topShoot.getPIDController().setReference(top * Constants.SHOOTER_MAX_VELOCITY, ControlType.kVelocity);
		botShoot.getPIDController().setReference(bot * Constants.SHOOTER_MAX_VELOCITY, ControlType.kVelocity);
	}
	public void pidShootPlus(double top, double bot) {
		double topTarget = top * Constants.SHOOTER_MAX_VELOCITY, botTarget = bot * Constants.SHOOTER_MAX_VELOCITY;

		if(topShoot.getEncoder().getVelocity() > .7 * topTarget) {
			topShoot.set(-1);
		}
		else {
			topShoot.getPIDController().setReference(topTarget, ControlType.kVelocity);
		}

		if(botShoot.getEncoder().getVelocity() > .7 * botTarget) {
			botShoot.set(-1);
		}
		else {
			botShoot.getPIDController().setReference(botTarget, ControlType.kVelocity);
		}

	}
	public void easyShoot(double power) {
		if(topShoot.getEncoder().getVelocity() >= -.6 * Constants.SHOOTER_MAX_VELOCITY * power) {
			topShoot.set(-.6 * power);
		}
		else {
			topShoot.set(0);
		}
		if(botShoot.getEncoder().getVelocity() >= -1 * Constants.SHOOTER_MAX_VELOCITY * power) {
			botShoot.set(-1 * power);
		}
		else {
			botShoot.set(0);
		}
	}

	public void setFeeder(double pow) {
		feeder.set(pow);
	}
	public void setClimber(double pow) {
		climber.set(pow);
	}

	public boolean getIntakeSensor() {
		return intakeSensor.get();
	}
	public boolean getShooterSensor() {
		return shooterSensor.get();
	}

	public double getTopVelocity() {
		return topShoot.getEncoder().getVelocity();
	}
	public double getBottomVelocity() {
		return botShoot.getEncoder().getVelocity();
	}
	
	//Full extension at 27000
	public int getClimbEncoder() {
		return climber.getSelectedSensorPosition() - climbEncoderOffset;
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("climber encoder", getClimbEncoder());
		System.out.println("top: " + topShoot.getEncoder().getVelocity() + " bot: " + botShoot.getEncoder().getVelocity());
	}
}
