package com.gojek.parkinglot.dataaccess.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gojek.parkinglot.dataaccess.ParkingLotManager;
import com.gojek.parkinglot.dataaccess.data.ParkingLotDataInformation;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.parking.car.CarParkingService.ParkingInformation;

/**
 * @author Shashanka
 *
 * @param <T>
 */
public class CarParkingLotManager<T extends Vehicle> implements ParkingLotManager<T> {

	@SuppressWarnings("rawtypes")
	private Map<Integer, ParkingLotDataInformation> levelInfoMap;
	@SuppressWarnings("rawtypes")
	public static CarParkingLotManager instance = null;

	private CarParkingLotManager() throws ParkingException {
		if(instance != null) {
			throw new ParkingException("Cannot instantiate object more than once !!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	private CarParkingLotManager(HashMap<Integer, ParkingInformation> info) throws ParkingException {
		if(instance != null) {
			throw new ParkingException("Cannot instantiate object more than once !!");
		}
		
		if(levelInfoMap == null) {
			levelInfoMap = new HashMap<Integer, ParkingLotDataInformation>();
		}
		
		for(int key: info.keySet()) {
			levelInfoMap.put(key, ParkingLotDataInformation.getInstance(key, info.get(key)));
		}
	}

	

	@SuppressWarnings("unchecked")
	public static <T extends Vehicle> CarParkingLotManager<T> getInstance() throws ParkingException{
		if (instance == null) {
			synchronized (CarParkingLotManager.class) {
				if (instance == null) {
					instance = new CarParkingLotManager<T>();
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Vehicle> CarParkingLotManager<T> getInstance(HashMap<Integer, ParkingInformation> info) throws ParkingException {
		if (instance == null) {
			synchronized (CarParkingLotManager.class) {
				if (instance == null) {
					instance = new CarParkingLotManager<T>(info);
				}
			}
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public int parkCar(int level, Vehicle vehicle) {
		return levelInfoMap.get(level).parkCar(vehicle);
	}

	public boolean leaveCar(int level, int slot) {
		return levelInfoMap.get(level).leaveCar(slot);
	}

	@SuppressWarnings("unchecked")
	public List<String> getStatus(int level) {
		return levelInfoMap.get(level).getStatus();
	}

	@SuppressWarnings("unchecked")
	public List<String> getRegistrationNumbersFromColor(int level, String color) {
		return levelInfoMap.get(level).getRegNumberForColor(color);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getSlotNumbersFromColor(int level, String color) {
		return levelInfoMap.get(level).getSlotNumbersFromColor(color);
	}

	public int getSlotNumberFromRegistrationNumber(int level, String registrationNumber) {
		return levelInfoMap.get(level).getSlotNoFromRegistrationNo(registrationNumber);
	}

	public int getFreeSlotsCount(int level) {
		return levelInfoMap.get(level).getFreeSlotsCount();
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@SuppressWarnings("unchecked")
	public void clean() {
		for(ParkingLotDataInformation<T> inf: levelInfoMap.values()) {
			inf.doCleanUp();
		}
		levelInfoMap.clear();
		levelInfoMap = null;
		instance = null;
	}

}
