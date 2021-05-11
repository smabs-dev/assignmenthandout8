package week5;

import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class FlightPlanner extends ConsoleProgram {
	/** Runs the program. */
	public void run() {
		println("Welcome to Flight Planner!");
		readFlightData("flights.txt");
		println("Here's a list of all the cities in our database:");
		printCityList(cities);
		println("Let's plan a round-trip route!");

		String startCity = readLine("Enter the starting city: ");
		ArrayList<String> route = new ArrayList<String>();
		route.add(startCity);
		String currentCity = startCity;
		while (true) {
			String nextCity = getNextCity(currentCity);
			route.add(nextCity);

			if (nextCity.equals(startCity))
				break;
			currentCity = nextCity;
		}
		printRoute(route);
	}

	// Ask user for their next destination
private String getNextCity(String city) {
		ArrayList<String> destinations = getDestinations(city);
		String nextCity = null;
		while (true) {
			println("From " + city + " you can fly directly to:");
			printCityList(destinations);
			String prompt = "Where do you want to go from " + city + "? ";
			nextCity = readLine(prompt);
		if (destinations.contains(nextCity)) break;
			println("You can't get to that city by a direct flight.");
		}
		return nextCity;
		}

	/*
	 * Looks up the destinations from a starting city in the
	 * HashMap of flights, and returns an array list
	 */
private ArrayList<String> getDestinations(String fromCity) {
		return flights.get(fromCity);
	}


	 // Prints a list of cities from the array list. 
		
private void printCityList(ArrayList<String> cityList) {
		for (int i = 0; i < cityList.size(); i++) {
			String city = cityList.get(i);
			println(" " + city);
		}
	}

	/**
	 * Prints out the flight route, with a " -> " in between.
	 */
private void printRoute(ArrayList<String> route) {
		println("The route you've chosen is: ");
		for (int i = 0; i < route.size(); i++) {
			if (i > 0)
				print(" -> ");
			print(route.get(i));
		}
		println();
	}

private void readFlightData(String filename) {
		flights = new HashMap<String,ArrayList<String>>();
		cities = new ArrayList<String>();
	try {
		BufferedReader rd = 
		new BufferedReader(new FileReader(filename));
		
	while (true) {
		String line = rd.readLine();
		
	if (line == null) break;
	
	if (line.length() != 0) {
		readFlightEntry(line);
		}
	}
		rd.close();
		} catch (IOException ex) {
		throw new ErrorException(ex);
		
		}
	}

	/**
	 * Reads a single flight entry from the line passed as an argument,
	 * fromCity -> toCity
	 * Each new city is added to the ArrayList "cities", new flights are
	 * recorded by adding a new destination city to the ArrayList stored in the
	 * HashMap flights for the starting city.
	 */
private void readFlightEntry(String line) {
		int arrow = line.indexOf("->");
		if (arrow == -1) {
			throw new ErrorException("Illegal flight entry " + line);
		}
		// Note: trim() removes leading/ending spaces from a string
		String fromCity = line.substring(0, arrow).trim();
		String toCity = line.substring(arrow + 2).trim();
		defineCity(fromCity);
		defineCity(toCity);
		getDestinations(fromCity).add(toCity);
	}

	/**
	 * Defines a city by entering it in the cities array and entering an empty ArrayList in the
	 * flights table to show that it has no set destinations.
	 */
private void defineCity(String cityName) {
		if (!cities.contains(cityName)) {
			cities.add(cityName);
			flights.put(cityName, new ArrayList<String>());
		}
	}

	/* Private instance variables */
private Map<String, ArrayList<String>> flights;
private ArrayList<String> cities;

}
