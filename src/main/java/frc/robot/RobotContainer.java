package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.DefaultAngler;
import frc.robot.commands.DefaultCannon;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.DefaultIntake;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.DigitalInput;

public class RobotContainer {

	/*
	To do:
	- Check orientation of drivetrain encoders, invert?
	- Tune shooter velocity PID
	- Make combined intake/index command using TOF/beam break sensors
	- Make function that raises the cannon to a certain height (not just setting dart power)
	- Tune Limelight auto-aim
	- Do auto
	- LED subsystem/disco party button
	*/

	private final Drivetrain drivetrain;
	private final Intake intake;
	private final Cannon cannon;
	private final Anglers anglers; //no they are not fishermen;

	Joystick stick;
	XboxController xbox;

	DigitalInput testIndex;

	public RobotContainer() {
		stick = new Joystick(0);
		xbox = new XboxController(1);

		drivetrain = new Drivetrain();
		intake = new Intake();
		cannon = new Cannon();
		anglers = new Anglers();
		
		setDefaultCommands();
		configureButtonBindings();
	}

	private void setDefaultCommands() {
		drivetrain.setDefaultCommand(new DefaultDrive(() -> stick.getY(), () -> stick.getZ(), () -> stick.getRawButton(2), drivetrain));   
		intake.setDefaultCommand(new DefaultIntake(() -> stick.getRawButton(11), intake));
		cannon.setDefaultCommand(new DefaultCannon(() -> stick.getRawButton(1), () -> stick.getRawButton(12), cannon));
		anglers.setDefaultCommand(new DefaultAngler(() -> stick.getRawButton(7), () -> stick.getRawButton(8),
													() -> stick.getRawButton(9), () -> stick.getRawButton(10), anglers));
	}

	private void configureButtonBindings() {
		
	}

	private double getClimbPow(){
		if(xbox.getBumper(Hand.kRight)) {
			return .5;
		}
		else if(xbox.getBumper(Hand.kLeft)) {
			return -.5;
		}
		else {
			return 0;
		}
	}
}
