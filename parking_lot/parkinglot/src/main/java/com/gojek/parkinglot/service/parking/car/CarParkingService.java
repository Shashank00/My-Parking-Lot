package com.gojek.parkinglot.service.parking.car;

import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.parking.ParkingService;

public class CarParkingService implements ParkingService {

	
	public void createParkingLot(int level, int capacity) throws ParkingException {
		// TODO Auto-generated method stub
		
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
