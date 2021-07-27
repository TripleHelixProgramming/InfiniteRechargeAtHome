// package frc.robot.drivetrain;

// public class StateEstimator {
//     private double leftDistance, rightDistance, heading, x, y, theta, localX, localY;

//     public StateEstimator(double leftDistance,
//                         double rightDistance,
//                         double heading,
//                         double x,
//                         double y,
//                         double theta) {
//         this.leftDistance = leftDistance;
//         this.rightDistance = rightDistance;
//         this.heading = heading;
//         this.x0 = x;
//         this.y0 = y;
//         this.theta = theta;
//     }

//     public double[] update(double left, double right, double theta) {
//         double deltaTheta = theta - this.theta - heading;
//         double linearDistance = (left - leftDistance + right - rightDistance) * 0.5;
//         this.leftDistance = left;
//         this.rightDistance = right;
        
//         if (Math.abs(deltaTheta) < 1E-3) {
//             localX = linearDistance * Math.cos(theta);
//             localY = linearDistance * Math.sin(theta);
//         } else {
//             localX = linearDistance * Math.sin(deltaTheta) / deltaTheta;
//             localY = linearDistance * (1 - Math.cos(deltaTheta)) / deltaTheta;
//         }

//         this.x += localX * Math.cos(theta) - localY * Math.sin(theta);
//         this.y += localX * Math.sin(theta) + localY * Math.cos(theta);
//         this.theta = theta - heading;

//         double[] pose = {this.x, this.y, this.theta};
//         return pose;
//     }
// }