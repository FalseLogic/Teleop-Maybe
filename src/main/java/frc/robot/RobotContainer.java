package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
	DigitalInput topLim, botLim, leadLim;

	DigitalInput testIndex;

	public RobotContainer() {
		stick = new Joystick(0);
		xbox = new XboxController(1);

		drivetrain = new Drivetrain();
		intake = new Intake();
		cannon = new Cannon();
		anglers = new Anglers();

		topLim = new DigitalInput(0);
		botLim = new DigitalInput(1);
		leadLim = new DigitalInput(2);

		testIndex = new DigitalInput(3);
		
		setDefaultCommands();
		configureButtonBindings();
	}

	private void setDefaultCommands() {
		drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.arcadeDrive(stick.getY() * (stick.getRawButton(1) ? 1 : -1), stick.getZ()), drivetrain));   
		//intake.setDefaultCommand(new RunCommand(() -> intake.intakeComs(getSuckPow(), getArmPow()), intake));
		//cannon.setDefaultCommand(new RunCommand(() -> cannon.cannonComs(getShoot(), getFeed(), getClimbPow()), cannon));
		//cannon.setDefaultCommand(new RunCommand(() -> cannon.pidShoot(stick.getY(), 0), cannon));
		//anglers.setDefaultCommand(new RunCommand(() -> anglers.angleComs(getDartPow(), getLeadPow()), anglers));
	}

	private void configureButtonBindings() {
		
	}

	private double getArmPow() {
		if(xbox.getYButton()) {
			return .5;
		}
		else if(xbox.getAButton()) {
			return -.5;
		}
		else {
			return 0;
		}
	}

	private double getSuckPow() {
		if(xbox.getXButton()) {
			return .7;
		}
		else if(xbox.getBButton()) {
			return -.7;
		}
		else {
			return 0;
		}
	}

	private boolean getShoot() {
		if(xbox.getTriggerAxis(Hand.kRight) > .6) {
			return true;
		}
		else {
			return false;
		}
	}

	private double getFeed() {
		if(xbox.getTriggerAxis(Hand.kLeft) > .6)
			return -.8;
		else if (xbox.getBackButton())
			return .6;
		else
			return 0;
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

	private double getDartPow(){
		if(botLim.get() && stick.getRawButton(8))
			return -.5;
		else if(topLim.get() && stick.getRawButton(7))
			return .5;
		else
			return 0;
	}

	private double getLeadPow(){
		if(!leadLim.get() && stick.getRawButton(9))
			return 1;
		else if(stick.getRawButton(10))
			return -1;
		else
			return 0;
	}

}
