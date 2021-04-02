package frc.com.team319.trajectory;

import java.util.List;
import frc.com.team319.ui.DraggableWaypoint;

public class BobPath {
	private String name;
	private List<DraggableWaypoint> waypoints;

	/**
	 * Creates a <code>BobPath</code> with a name and a <code>List</code>
	 * of type <code>DraggableWaypoint</code>.
	 * 
	 * @param name
	 * @param waypoints
	 */
	public BobPath(String name, List<DraggableWaypoint> waypoints) {
		this.name = name;
		this.waypoints = waypoints;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the waypoints
	 */
	public List<DraggableWaypoint> getWaypoints() {
		return waypoints;
	}

	/**
	 * @param waypoints the waypoints to set
	 */
	public void setWaypoints(List<DraggableWaypoint> waypoints) {
		this.waypoints = waypoints;
	}

	/**
	 * Returns a a comma-deliminated <code>String</code> that contains
	 * each waypoint on a separate line.
	 * 
	 * @return a <code>String</code> representing the path
	 */
	@Override
	public String toString() {
		StringBuilder pathString = new StringBuilder();
		for (DraggableWaypoint waypoint : waypoints) {
			pathString.append(waypoint.toString()).append("\n");
		}
		String pathsString = pathString.toString();
		return pathsString.substring(0, Math.max(pathsString.length() - 1, 0));
	}
}
