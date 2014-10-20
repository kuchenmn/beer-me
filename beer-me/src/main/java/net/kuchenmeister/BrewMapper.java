package net.kuchenmeister;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrewMapper {

	private static final String BEER_MAPPING_KEY = "f5352d30091fc64c74f881ae8e5f84d1";
	private static final String BEERMAPPING_URL = "http://beermapping.com/webservice/";
	private static final String LOCCITY_SERVICE = "loccity";
	private static final String LOCSTATE_SERVICE = "locstate";
	private static final String LOCMAP_SERVICE = "locmap";
	private static final String LOCSCORE_SERVICE = "locscore";
	private static final String PATH_DELIM = "/";

	public static List<Location> getLocations(String searchString) throws Exception {
		Map<String, String> ids = new HashMap<>();
		StringBuffer request = new StringBuffer(BEERMAPPING_URL);
		if (searchString.contains(",")) {
			request.append(LOCCITY_SERVICE);
		} else {
			request.append(LOCSTATE_SERVICE);
		}
		request.append(PATH_DELIM).append(BEER_MAPPING_KEY).append(PATH_DELIM).append(searchString);
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request.toString());

		// Send GET request
		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusText());
		}
		InputStream resultStream = null;

		// Get the response body
		resultStream = method.getResponseBodyAsStream();

		// Process response
		Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resultStream);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath=factory.newXPath();

		//Get all search Result nodes
		NodeList nodes = (NodeList)xPath.evaluate("/bmp_locations/location", response, XPathConstants.NODESET);
		int nodeCount = nodes.getLength();
		//iterate over search Result nodes

		List<Location> locations = new ArrayList<>();
		for (int i = 0; i < nodeCount; i++) {
			//Get each xpath expression as a string
			String id = (String)xPath.evaluate("id", nodes.item(i), XPathConstants.STRING);
			String name = (String)xPath.evaluate("name", nodes.item(i), XPathConstants.STRING);
			String status = (String)xPath.evaluate("status", nodes.item(i), XPathConstants.STRING);
			locations.add(new Location(id, name, status));
		}
		return locations;
	}

	public static LatLng getLatLng(String id) throws Exception {
		String request = BEERMAPPING_URL + LOCMAP_SERVICE + PATH_DELIM + BEER_MAPPING_KEY + PATH_DELIM + id;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);

		// Send GET request
		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusText());
		}
		InputStream resultStream = null;

		// Get the response body
		resultStream = method.getResponseBodyAsStream();

		// Process response
		Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resultStream);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath=factory.newXPath();

		String lat = (String)xPath.evaluate("/bmp_locations/location/lat", response, XPathConstants.STRING);
		String lon = (String)xPath.evaluate("/bmp_locations/location/lng", response, XPathConstants.STRING);

		return new LatLng(lat, lon);
	}

	public static String getScore(String id) throws Exception {
		String request = BEERMAPPING_URL + LOCSCORE_SERVICE + PATH_DELIM + BEER_MAPPING_KEY + PATH_DELIM + id;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);

		// Send GET request
		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusText());
		}
		InputStream resultStream = null;

		// Get the response body
		resultStream = method.getResponseBodyAsStream();

		// Process response
		Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resultStream);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath=factory.newXPath();

		return (String)xPath.evaluate("/bmp_locations/location/overall", response, XPathConstants.STRING);
	}
}
