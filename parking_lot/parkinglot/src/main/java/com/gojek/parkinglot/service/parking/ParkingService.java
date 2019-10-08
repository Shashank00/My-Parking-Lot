package com.gojek.parkinglot.service.parking;

import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;

public interface ParkingService {

	public void createParkingLot(int level, int capacity) throws ParkingException;

	public void park(int level, Vehicle vehicle);

	public void remove(int level, int slotNo) throws ParkingException;

	public void updateStatus(int level);

	public void getStatus(int level);

	public void getRegistrationNumbersForColor(int level, String color) throws ParkingException;

	public void getSlotNumbersFromColor(int level, String color) throws ParkingException;

	public int getSlotNumberFromRegistrationNo(int level, String registrationNo) throws ParkingException;

}