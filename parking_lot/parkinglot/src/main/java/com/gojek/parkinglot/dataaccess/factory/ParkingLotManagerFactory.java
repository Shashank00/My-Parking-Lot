package com.gojek.parkinglot.dataaccess.factory;

import java.util.HashMap;

import com.gojek.parkinglot.constants.vehicle.VehicleType;
import com.gojek.parkinglot.dataaccess.ParkingLotManager;
import com.gojek.parkinglot.dataaccess.implementation.CarParkingLotManager;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.service.parking.car.CarParkingService.ParkingInformation;

public class ParkingLotManagerFactory {
	
	@SuppressWarnings("rawtypes")
	public static <T> ParkingLotManager getManagerInstance(HashMap<Integer, ParkingInformation> info, VehicleType type) throws ParkingException {
		
		@SuppressWarnings("rawtypes")
		ParkingLotManager manager = null;
		if(type == VehicleType.CAR) {
			manager = CarParkingLotManager.getInstance(info);
		}
		
		return manager;
	}
}
