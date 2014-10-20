package net.kuchenmeister;

import java.util.Map;

public class LatLng {
	private String lat;
	private String lng;

	public LatLng(String lat, String lon) {
		this.lat = lat;
		this.lng = lon;
	}

	public LatLng(Map<String, String> latLngMap) {
		for (String key : latLngMap.keySet()) {
			this.lat = key;
			this.lng = latLngMap.get(key);
		}
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
