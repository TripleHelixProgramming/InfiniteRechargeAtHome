// package frc.robot.drivetrain.commands;

// import frc.paths.Path;
// import frc.robot.drivetrain.StateEstimator;
// import frc.robot.drivetrain.Drivetrain;

// import edu.wpi.first.wpilibj.command.Command;

// public class TrajectoryFollower extends Command {
//     private StateEstimator odometry;
//     private Path path;

//     public TrajectoryFollower(Path path) {
//         this.path = path;
//     }

//     @Override
//     protected void initialize() {
//         double[] state = path.getPath()[0];
//         odometry = new StateEstimator(getLeftDistance(), 
//                                     getRightDistance(), 
//                                     getHeading(), 
//                                     state[4], 
//                                     state[5], 
//                                     state[6]);
//     }

//     @Override
//     protected void execute() {
//         double[] pose = odometry.update(getLeftDistance(), getRightDistance(), getHeading());
//     }

//     @Override
//     protected boolean isFinished() {
//       return isFinished;
//     }
  
//     @Override
//     protected void end() {

//     }
  
//     @Override
//     protected void interrupted() {
//       end();
//     }

//     private double getLeftDistance() {
//         Drivetrain.getDrivetrain().getLeftPosition();
//     }

//     private double getRightDistance() {
//         Drivetrain.getDrivetrain().getRightPosition();
//     }

//     private double getHeading() {
//         return Math.toRadians(drivetrain.getHeading());
//     }
// }