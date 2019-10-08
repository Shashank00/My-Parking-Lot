package com.gojek.parkinglot.dataaccess.implementation;

import java.util.List;

import com.gojek.parkinglot.dataaccess.ParkingLotManager;
import com.gojek.parkinglot.model.Vehicle;

public class CarParkingLotManager<T extends Vehicle> implements ParkingLotManager<T> {

	
	
	public int parkCar(int level, Vehicle vehicle) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean leaveCar(int level, int slot) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getStatus(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getRegistrationNumbersFromColor(int level, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Integer> getSlotNumbersFromColor(int level, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSlotNumberFromRegistrationNumber(int level, String registrationNumber) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFreeSlotsCount(int level) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
