package com.gojek.parkinglot.service.parking;

import com.gojek.parkinglot.service.AbstractService;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;

public interface ParkingService extends AbstractService {
	
	public void createParkingLot(int level, int capacity) throws ParkingException;
	public void park(int level, Vehicle vehicle);
}
