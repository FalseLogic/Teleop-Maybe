package frc.robot.autocommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.LimelightShoot;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class CenterThreeBallAuto extends SequentialCommandGroup {
    public CenterThreeBallAuto(Drivetrain drivetrain, Anglers anglers, Cannon cannon, Intake intake) {
        addCommands(
            new RunCommand(() -> anglers.setDartSafely(-.5), anglers).withTimeout(1).andThen(new InstantCommand(() -> anglers.setDartSafely(0))),
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain)
			),
			new ParallelDeadlineGroup(
				new WaitCommand(3),
				new LimelightShoot(cannon, drivetrain.getLimelight())
			),
			new AutoDrive(45, -.55, drivetrain)
        );
    }
}