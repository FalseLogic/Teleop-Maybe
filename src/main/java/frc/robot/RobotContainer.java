package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.AutoTurn;
import frc.robot.commands.BasicShoot;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.LimelightShoot;
import frc.robot.defaultcommands.DefaultAngler;
import frc.robot.defaultcommands.DefaultCannon;
import frc.robot.defaultcommands.DefaultDrive;
import frc.robot.defaultcommands.DefaultIntake;
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
	X Tune Limelight auto-aim
	- Do auto
	- LED subsystem/disco party button
	*/

	private final Drivetrain drivetrain;
	private final Intake intake;
	private final Cannon cannon;
	private final Anglers anglers; //no they are not fishermen;

	private final Joystick stick;
	private final XboxController xbox;

	private SendableChooser<Command> autoChooser;

	public RobotContainer() {
		stick = new Joystick(0);
		xbox = new XboxController(1);

		drivetrain = new Drivetrain();
		intake = new Intake();
		cannon = new Cannon();
		anglers = new Anglers();
		
		setDefaultCommands();
		configureButtonBindings();
		putAutoChooser();
	}

	public void autoInit() {
		drivetrain.resetAngle();
		drivetrain.resetEncoders();
	}

	private void setDefaultCommands() {
		drivetrain.setDefaultCommand(new DefaultDrive(() -> stick.getY(), () -> stick.getZ(), () -> stick.getRawButtonPressed(1), drivetrain));   
		intake.setDefaultCommand(new DefaultIntake(() -> xbox.getXButton(), () -> xbox.getBButton(),
												   () -> xbox.getYButton(), () -> xbox.getAButton(), intake));
		cannon.setDefaultCommand(new DefaultCannon(() -> xbox.getBackButton(),
												   () -> xbox.getPOV() == 0, () -> xbox.getPOV() == 180, cannon));
		anglers.setDefaultCommand(new DefaultAngler(() -> stick.getRawButton(7), () -> stick.getRawButton(8), 
													() -> stick.getRawButton(11) && stick.getRawButton(12), anglers));
	}

	private void configureButtonBindings() { 

		new JoystickButton(xbox, 6).whenHeld(new BasicShoot(cannon));

		new JoystickButton(stick, 4).whenHeld(
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain))
		);

		new JoystickButton(xbox, 5).whenHeld(new LimelightShoot(cannon, drivetrain.getLimelight()));


	}

	private void putAutoChooser() {
		autoChooser = new SendableChooser<Command>();

		autoChooser.setDefaultOption("Nothing", new WaitCommand(10));
		autoChooser.addOption("Move Forward", new AutoDrive(10, -.4, drivetrain));
		autoChooser.addOption("Turn", new SequentialCommandGroup(
			new AutoTurn(90, .3, drivetrain),
			new AutoTurn(-45, -.3, drivetrain)
		));
		autoChooser.addOption("3 Balls", new SequentialCommandGroup(
			new RunCommand(() -> anglers.setDartSafely(-.5), anglers).withTimeout(1),
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain),
				new PrintCommand("message")
			),
			new ParallelDeadlineGroup(
				new WaitCommand(3),
				new LimelightShoot(cannon, drivetrain.getLimelight())
			),
			new AutoDrive(40, -.5, drivetrain)
		));
		autoChooser.addOption("6 Balls", new SequentialCommandGroup(
			new ParallelDeadlineGroup(
				new AutoTurn(-21, -.4, drivetrain),
				new RunCommand(() -> anglers.setDartSafely(-.6), anglers).withTimeout(1.5)
			),
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain)
			),
			new ParallelDeadlineGroup(
				new WaitCommand(3.25),
				new LimelightShoot(cannon, drivetrain.getLimelight())
			),
			new AutoTurn(-2, .34, drivetrain),
			new ParallelDeadlineGroup(
				new AutoDrive(130, .45, drivetrain),
				new DefaultCannon(() -> false, () -> false, () -> false, cannon),
				new RunCommand(() -> anglers.setDartSafely(-.8), anglers),
				new SequentialCommandGroup(
					new ParallelDeadlineGroup(
						new WaitCommand(.75),
						new RunCommand(() -> intake.setArm(.75), intake)
					),
					new ParallelCommandGroup(
						new RunCommand(() -> intake.setIntake(-1)),
						new RunCommand(() -> intake.setArm(.3), intake)
					)
				)
			),
			new AutoDrive(40, -.5, drivetrain),
			new AutoTurn(-19, -.4, drivetrain),
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain)
			),
			new ParallelDeadlineGroup(
				new WaitCommand(5),
				new LimelightShoot(cannon, drivetrain.getLimelight())
			)
		));

		//3.77

		

		SmartDashboard.putData("Autonomous Chooser", autoChooser);

	}

	public Command getAutoCommand() {
		return autoChooser.getSelected();
	}

	private class LLToggleButton extends Button {

		JoystickButton a = new JoystickButton(stick, 4), b = new JoystickButton(xbox, 5);

		public boolean get() {
			return a.get() || b.get();
		}

	}

}