package com.driver.controllers;

import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository;

    public String getLargestAirportName()
    {
        return airportRepository.getLargestAirportName();
    }
    public void addFlight (Flight flight)
    {
        airportRepository.addFlight(flight);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City city1, City city2)
    {
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(city1,city2);
    }

    public int getNumberOfPeople(Date date, String airportName)
    {
        return airportRepository.getNumberOfPeople(date,airportName);
    }

    public int flightFare(Integer flightId)
    {
        return airportRepository.flightFare(flightId);
    }

    public String bookATicket(Integer flightId,Integer passengerId)
    {
        return airportRepository.bookATicket(flightId, passengerId);
    }

    public String cancelTicket(Integer flightId,Integer passengerId)
    {
        return airportRepository.cancelTicket(flightId, passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId)
    {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String getStartingCityName(Integer flightId)
    {
        return airportRepository.getStartingCityName(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId)
    {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public String addPassenger(Passenger passenger)
    {
        return airportRepository.addPassenger(passenger);
    }
}
