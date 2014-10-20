package net.kuchenmeister;

public class Location {
	private String id;
	private String name = "No Name";
	private String status = "No Status";
	private LatLng latLng = new LatLng("No Lat", "No Lng");
	private String score = "0";

	public Location(String id, String name, String status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(latLng.getLat()).append("/").append(latLng.getLng()).append(" - ")
				                      .append(name).append(" - ").append(status).append(" - ").append(score);
		return buffer.toString();
	}
}
