package com.gojek.parkinglot.service.parking.car;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.gojek.parkinglot.dataaccess.ParkingLotManager;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.strategy.ParkingStrategy;
import com.gojek.parkinglot.model.strategy.implementation.NearestEntryParkingStrategy;
import com.gojek.parkinglot.service.parking.ParkingService;

public class CarParkingService implements ParkingService {

	private ParkingLotManager<Vehicle> manager  = null;
	
	private ReentrantReadWriteLock     lock 	= new ReentrantReadWriteLock();
	
	public static class ParkingInformation {
		
		Integer capacity;
		ParkingStrategy strategy;
		
		public ParkingInformation(Integer capacity, ParkingStrategy strategy) {
			this.capacity = capacity;
			this.strategy = strategy;
		}
	}
	
	public void createParkingLot(int level, int capacity) throws ParkingException {
		if (manager != null) {
			throw new ParkingException("Parking Manager is already active !!");
		}

		HashMap<Integer, ParkingInformation> floorInfo = new HashMap<Integer, ParkingInformation>();

		floorInfo.put(level, new ParkingInformation(capacity, new NearestEntryParkingStrategy()));
		
		

	}

	public void park(int level, Vehicle vehicle) {
		// TODO Auto-generated method stub
		
	}

	public void remove(int level, int slotNo) throws ParkingException {
		// TODO Auto-generated method stub
		
	}

	public void updateStatus(int level) {
		// TODO Auto-generated method stub
		
	}

	public void getStatus(int level) {
		// TODO Auto-generated method stub
		
	}

	public void getRegistrationNumbersForColor(int level, String color) throws ParkingException {
		// TODO Auto-generated method stub
		
	}

	public void getSlotNumbersFromColor(int level, String color) throws ParkingException {
		// TODO Auto-generated method stub
		
	}

	public int getSlotNumberFromRegistrationNo(int level, String registrationNo) throws ParkingException {
		// TODO Auto-generated method stub
		return 0;
	}

}
