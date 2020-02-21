package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.DigitalInput;

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
		drivetrain.setDefaultCommand(new DefaultDrive(() -> stick.getY(), () -> stick.getZ(), () -> !stick.getRawButton(2), drivetrain));   
		intake.setDefaultCommand(new DefaultIntake(() -> stick.getRawButton(11), intake));
		cannon.setDefaultCommand(new DefaultCannon(() -> stick.getRawButton(12), cannon));
		anglers.setDefaultCommand(new DefaultAngler(() -> stick.getRawButton(7), () -> stick.getRawButton(8),
													() -> stick.getRawButton(5), () -> stick.getRawButton(6), anglers));
	}

	private void configureButtonBindings() {
		new JoystickButton(stick, 1).whenHeld(new BasicShoot(cannon));

		new JoystickButton(stick, 4).whenHeld(
			new SequentialCommandGroup(
				new ParallelCommandGroup(
					new LimelightAngle(drivetrain.getLimelight(), anglers),
					new LimelightDrive(drivetrain)),
				new LimelightShoot(cannon, drivetrain)
			)
		);

		new JoystickButton(stick, 9).whenHeld(new RunCommand(() -> cannon.setTopShoot(.2), cannon));
		new JoystickButton(stick, 10).whenHeld(new RunCommand(() -> cannon.setBottomShoot(.2), cannon));

	}
}
