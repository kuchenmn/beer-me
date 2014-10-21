package net.kuchenmeister;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

public class BeerMe {

	public static void main(String[] args) throws Exception {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(BeerMe.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("locations.ftl");
                    Map<String, Object> citiesMap = new HashMap<>();
                    citiesMap.put("cities", getCities());
                    citiesMap.put("citySelected", "none");
                    citiesMap.put("locationTypes", getLocationTypes());
                    template.process(citiesMap, writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });

        Spark.post(new Route("/search") {
            @Override
            public Object handle(Request request, Response response) {
                final String city = request.queryParams("city");
                List<String> filters = getFilters(request);
                StringWriter writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("locations.ftl");
                    List<Location> locations = findLocations(city).stream()
                            .filter(location -> filters.stream().anyMatch(location.getStatus()::equals))
                            .collect(Collectors.toList());
                    Map<String, Object> locationsMap = new HashMap<>();
                    locationsMap.put("locations", locations);
                    locationsMap.put("cities", getCities());
                    locationsMap.put("citySelected", city);
                    locationsMap.put("locationTypes", getLocationTypes());
                    ObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_21).build();
//                    template.process(getCities(), writer);
                    template.process(locationsMap, writer, wrapper);
                    System.out.println(writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });


//		long start = System.nanoTime();
//		List<Location> locations = new ArrayList<Location>();
		// find locations by city or state
//		locations = findLocationsByCityOrState("Minneapolis,MN");

		// find breweries only
//		List<Location> allLocations = findLocations("Minneapolis,MN");
//		for (Location location : allLocations) {
//			if (location.getStatus().equals("Brewery")) {
//				locations.add(location);
//			}
//		}
//		populateAdditionalInfo(locations);
		// -------------
		// stream version
//		locations = findLocations("Minneapolis,MN").stream()
//				                                    .filter(location -> location.getStatus().equals("Brewery"))
//				                                    .collect(Collectors.toList());
//		populateAdditionalInfo(locations);
		// -------------

		// find breweries only with a score 85 and above
//		List<Location> allLocations = findLocations("Minneapolis,MN");
//		populateAdditionalInfo(allLocations);
//		for (Location location : allLocations) {
//			if (location.getStatus().equals("Brewery")) {
//				if (Double.valueOf(location.getScore()) >= 85) {
//					locations.add(location);
//				}
//			}
//		}

		// -------------
		// stream version
//		locations = findLocations("Minneapolis,MN")
//				             .parallelStream()
//				             .map(location -> populateAdditionalInfo(location))
//				             .filter(location -> location.getStatus().equals("Brewery"))
//							 .filter(location -> Double.valueOf(location.getScore()) >= 85)
//				             .collect(Collectors.toList());
//		// -------------
//
//		long end = System.nanoTime();
//		System.out.println("Locations Found: " + locations.size());
//		System.out.println("Time: " + (end - start)/1.0e9);
//		for (Location location : locations) {
//			System.out.println(location.toString());
//		}
	}

    // TODO: This should be functionalized
    private static List<String> getFilters(Request request) {
        List<String> filters = new ArrayList<>();
        for (String type : getLocationTypes()) {
            filters.add(request.queryParams(type));
        }
        return filters;
    }

    private static List<String> getLocationTypes() {
        return Arrays.asList("Beer Bar", "Beer Store", "Brewery", "Brewpub", "Homebrew Store");
    }

    private static List<String> getCities() {
        return Arrays.asList("Minneapolis,MN", "StPaul,MN", "Durango,CO", "MN", "WI");
    }

	private static List<Location> findLocationsByCityOrState(String searchString) {
		List<Location> locations = findLocations(searchString);
		System.out.println("Found " + locations.size() + " locations");
//		populateAdditionalInfo(locations);
		return locations;
	}

	private static List<Location> findLocations(String searchString) {
		List<Location> locations = new ArrayList<Location>();
		try {
			locations =  BrewMapper.getLocations(searchString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locations;
	}

	private static void populateAdditionalInfo(List<Location> locations) {
		for (Location location : locations) {
			try {
				location.setLatLng(BrewMapper.getLatLng(location.getId()));
				location.setScore(ScoreService.getScore(location.getId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Location populateAdditionalInfo(Location location) {
		try {
			location.setLatLng(BrewMapper.getLatLng(location.getId()));
			location.setScore(ScoreService.getScore(location.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}
}
