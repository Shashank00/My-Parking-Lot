package com.gojek.parkinglot.service.parking.factory;

import com.gojek.parkinglot.constants.vehicle.VehicleType;
import com.gojek.parkinglot.service.parking.ParkingService;
import com.gojek.parkinglot.service.parking.car.CarParkingService;

public class ParkingServiceFactory {
	
	public static ParkingService getParkingServiceInstance(VehicleType type) {
		if(type == VehicleType.CAR) {
			ParkingService service = new CarParkingService();
			return service;
		} else {
			return null;
		}
	}
}
