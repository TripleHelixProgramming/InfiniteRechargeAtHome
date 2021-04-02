// package com.team2363.trajectory.constraints;

// import com.team2363.geometry.Pose2d;

// public class CentripetalAccelerationConstraint extends Constraint {
//     private double maxCentripetalAcceleration;

//     public CentripetalAccelerationConstraint(double maxCentripetalAcceleration) {
//         this.maxCentripetalAcceleration = maxCentripetalAcceleration;
//     }

//     public double getMaxVelocity(Pose2d pose, double curvature) {
//         return Math.sqrt(maxCentripetalAcceleration / curvature);
//     }

//     public double getMaxAcceleration(Pose2d pose, double curvature) {
//         return null;
//     }
// }