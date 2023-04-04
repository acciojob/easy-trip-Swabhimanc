package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {

    class Ticket{
        Integer passengerID;
        Integer flightID;
        Ticket(int passengerID, int flightID)
        {
            this.passengerID=passengerID;
            this.flightID = flightID;
        }
    }

    List<Airport> airportDB = new ArrayList<>();
    HashMap<Integer,Flight> flightDB = new HashMap<>(); //flightID, Flight


    HashMap<Integer,Passenger> passengerDB = new HashMap<>();

    ArrayList<Ticket> ticketDB = new ArrayList<>(); //passengerID, flightID

    HashMap<Integer,Integer> revenueDB = new HashMap<>(); //flightID, revenue

    HashMap<Integer,Integer> flightFilledDB = new HashMap<>(); //flightID, noOfTicketBooked



    public String getLargestAirportName()
    {
        int max = 0;
        String ans = "";
        for(Airport x : airportDB)
        {
            if(max<x.getNoOfTerminals())
            {
                max= x.getNoOfTerminals();
                ans = x.getAirportName();
            }
            else if(max==x.getNoOfTerminals())
            {
                if(ans.compareTo(x.getAirportName())>0)
                {
                    ans = x.getAirportName();
                }
            }
        }
        return ans;
    }


    public double getShortestDurationOfPossibleBetweenTwoCities(City city1, City city2)
    {
        double duration = Double.MAX_VALUE;
        for(Integer flightID : flightDB.keySet())
        {
            Flight x = flightDB.get(flightID);
            if(x.getToCity().equals(city2) && x.getFromCity().equals(city1))
            {
                duration = Math.min(duration,x.getDuration());
            }
        }
        return duration==Double.MAX_VALUE ? -1 : duration;
    }

    public int getNumberOfPeople(Date date, String airportName)
    {
        City city = null;
        for(Airport x : airportDB)
        {
            if(x.getAirportName().equals(airportName))
            {
                city=x.getCity();
            }
        }
        int maxPassengers=0;

        for(Integer flightId : flightDB.keySet())
        {
            Flight f = flightDB.get(flightId);
            if(f.getFlightDate().equals(date) && (f.getFromCity().equals(city) || f.getToCity().equals(city)))
            {
                maxPassengers+=flightFilledDB.get(flightId);
            }
        }
        return maxPassengers;
    }

    public int flightFare(Integer flightId)
    {
        int count=flightFilledDB.get(flightId);
        return 3000+count*50;
    }


    public String bookATicket(Integer flightId,Integer passengerId)
    {
        Flight flight = flightDB.get(flightId);
        if(flight.getMaxCapacity()<=flightFilledDB.get(flightId))
        {
            return "FAILURE";
        }
        for(Ticket x : ticketDB)
        {
            if(x.passengerID==passengerId && x.flightID==flightId)
            {
                return "FAILURE";
            }
        }
        ticketDB.add(new Ticket(passengerId,flightId));
        revenueDB.put(flightId,revenueDB.getOrDefault(flightId,0)+flightFilledDB.getOrDefault(flightId,0)*50+3000);
        flightFilledDB.put(flightId,flightFilledDB.getOrDefault(flightId,0)+1);

        return "SUCCESS";
    }

    public String cancelTicket (Integer flightId,Integer passengerId)
    {
        for(Ticket x : ticketDB)
        {
            if(x.flightID==flightId && x.passengerID==passengerId)
            {
                ticketDB.remove(x);
                flightFilledDB.put(flightId,flightFilledDB.getOrDefault(flightId,0)-1);
                return "SUCCESSFUL";
            }
        }
        return "FAILURE";
    }

    public Integer countOfBookingsDoneByPassengerAllCombined(Integer passengerID)
    {
        int count=0;
        for(Ticket x : ticketDB)
        {
            if(x.passengerID==passengerID)
            {
                count++;
            }
        }
        return count;
    }

    public void addFlight(Flight flight)
    {
        flightDB.put(flight.getFlightId(),flight);
    }


    public String getStartingCityName(Integer flightId)
    {
        City city = flightDB.get(flightId).getFromCity();
        for(Airport x : airportDB)
        {
            if(x.getCity().equals(city))
            {
                return x.getAirportName();
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId)
    {
        return revenueDB.get(flightId);
    }

    public String addPassenger(Passenger passenger)
    {
        passengerDB.put(passenger.getPassengerId(),passenger);
        return "SUCCESS";
    }
}
