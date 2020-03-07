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
import frc.robot.defaultcommands.DefaultCannon;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class SixBallAuto extends SequentialCommandGroup {
    public SixBallAuto(Drivetrain drivetrain, Anglers anglers, Cannon cannon, Intake intake) {
        addCommands(
            new ParallelDeadlineGroup(
                new AutoTurn(-19, -.4, drivetrain),
                new RunCommand(() -> anglers.setDartSafely(-.6), anglers).withTimeout(1.5).andThen(new InstantCommand(() -> anglers.setDartSafely(0)))
            ),
            new ParallelCommandGroup(
                new LimelightAngle(drivetrain.getLimelight(), anglers),
                new LimelightDrive(drivetrain)
            ),
            new ParallelDeadlineGroup(
                new WaitCommand(3.25),
                new LimelightShoot(cannon, drivetrain.getLimelight())
            ),
            new AutoTurn(-8, .4, drivetrain),
            new ParallelDeadlineGroup(
                new AutoDrive(130, .55, drivetrain),
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
            new AutoDrive(10, -.5, drivetrain),
            new AutoTurn(-21, -.4, drivetrain),
            new ParallelCommandGroup(
                new LimelightAngle(drivetrain.getLimelight(), anglers),
                new LimelightDrive(drivetrain)
            ),
            new ParallelDeadlineGroup(
                new WaitCommand(5),
                new LimelightShoot(cannon, drivetrain.getLimelight())
            )
        );
    }
}