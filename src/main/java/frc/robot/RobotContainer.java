package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.BasicShoot;
import frc.robot.commands.DefaultAngler;
import frc.robot.commands.DefaultCannon;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.DefaultIntake;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.LimelightShoot;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class RobotContainer {

	/*
	To do:
	- Check orientation of drivetrain encoders, invert?
	X Tune shooter velocity PID
	X Make combined intake/index command using TOF/beam break sensors
	X Make function that raises the cannon to a certain height (not just setting dart power)
	- Tune Limelight auto-aim
	- Do auto
	- LED subsystem/disco party button
	*/

	private final Drivetrain drivetrain;
	private final Intake intake;
	private final Cannon cannon;
	private final Anglers anglers; //no they are not fishermen;

	private final Joystick stick;
	private final XboxController xbox;

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
		drivetrain.setDefaultCommand(new DefaultDrive(() -> stick.getY(), () -> stick.getZ(), () -> stick.getRawButtonPressed(1), drivetrain));   
		intake.setDefaultCommand(new DefaultIntake(() -> xbox.getXButton(), () -> xbox.getBButton(),
												   () -> xbox.getYButton(), () -> xbox.getAButton(), intake));
		cannon.setDefaultCommand(new DefaultCannon(() -> xbox.getBackButton(), cannon));
		anglers.setDefaultCommand(new DefaultAngler(() -> stick.getRawButton(7), () -> stick.getRawButton(8), anglers));
	}

	private void configureButtonBindings() { 

		new JoystickButton(xbox, 6).whenHeld(new BasicShoot(cannon));

		new JoystickButton(stick, 4).whenHeld(
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain))
		);

		new JoystickButton(xbox, 5).whenHeld(new LimelightShoot(cannon, drivetrain));

		//new JoystickButton(stick, 3).whenHeld(new PixyAuto(drivetrain, intake));
	}
}