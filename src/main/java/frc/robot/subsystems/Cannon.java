package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Cannon extends SubsystemBase {
	
	private final CANSparkMax topShoot, botShoot;
	private final WPI_TalonSRX feeder, climber;

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
		System.out.println(topShoot.getEncoder().getVelocity());
	}

	public void feed(double pow) {
		feeder.set(pow);
	}

	public void setClimb(double pow) {
		climber.set(pow);
	}

	public void cannonComs(boolean a, double b, double p){
		shoot(a);
		feed(b);
		setClimb(p);
	}

	@Override
	public void periodic() {
	}
}
