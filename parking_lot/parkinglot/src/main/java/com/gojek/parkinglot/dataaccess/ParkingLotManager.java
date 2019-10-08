package com.gojek.parkinglot.dataaccess;

import java.util.List;

import com.gojek.parkinglot.model.Vehicle;

public interface ParkingLotManager<T extends Vehicle> {
	
	public int parkCar(int level, T vehicle);
	
	public boolean leaveCar(int level, int slot);
	
	public List<String> getStatus(int level);
	
	public List<String> getRegistrationNumbersFromColor(int level, String color);
	
	public List<Integer> getSlotNumbersFromColor(int level, String color);
	
	public int getSlotNumberFromRegistrationNumber(int level, String registrationNumber);
	
	public int getFreeSlotsCount(int level);
	
	public void clean();
	
}
