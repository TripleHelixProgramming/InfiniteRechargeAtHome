package frc.com.team2363.geometry;

public class Pose2d {
    private Translation2d translation;
    private Rotation2d rotation;

    public Pose2d(Translation2d translation, Rotation2d rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Translation2d getTranslation() {
        return translation;
    }

    public Rotation2d getRotation() {
        return rotation;
    }

    public String toString() {
        return translation + "," + rotation;
    }
}