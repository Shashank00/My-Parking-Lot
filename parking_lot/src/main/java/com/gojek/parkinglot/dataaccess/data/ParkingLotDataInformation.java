package com.gojek.parkinglot.dataaccess.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.strategy.ParkingStrategy;
import com.gojek.parkinglot.service.parking.car.CarParkingService.ParkingInformation;

public class ParkingLotDataInformation<T extends Vehicle> {
	
	private AtomicInteger level    		 		 = new AtomicInteger(0);
	private AtomicInteger carSlots 		 		 = new AtomicInteger();
	@SuppressWarnings("unused")
	private AtomicInteger electricCarSlots 		 = new AtomicInteger();
	@SuppressWarnings("unused")
	private AtomicInteger motorBikeSlots 		 = new AtomicInteger();
	@SuppressWarnings("unused")
	private AtomicInteger truckSlots 		     = new AtomicInteger();
	
	private AtomicInteger freeCarSlots			 = new AtomicInteger();
	@SuppressWarnings("unused")
	private AtomicInteger freeElectricCarSlots 	 = new AtomicInteger();
	@SuppressWarnings("unused")
	private AtomicInteger freeMotorBikeSlots 	 = new AtomicInteger();
	@SuppressWarnings("unused")
	private AtomicInteger freeTruckSlots 		 = new AtomicInteger();
	
	// Strategy for assigning, removing slots to vehicles
	private ParkingStrategy strategy;
	
	private Map<Integer, Vehicle> carSlotMap;
	
	@SuppressWarnings({ "rawtypes" })
	private static ParkingLotDataInformation instance = null;
	
	private ParkingLotDataInformation(int level, ParkingInformation info) throws ParkingException {
		if(instance != null) {
			throw new ParkingException("Cannot instantiate more than one object for this singleton class");
		}
		this.level.set(level);
		this.carSlots.set(info.capacity);
		this.freeCarSlots.set(info.capacity);
		
		this.strategy = info.strategy;
		
		carSlotMap = new ConcurrentHashMap<Integer, Vehicle>();
		
		for(int i = 1; i <= info.capacity; i++) {
			this.strategy.add(i);
		}
	}
	
	private ParkingLotDataInformation() throws ParkingException {
		if(instance != null) {
			throw new ParkingException("Cannot instantiate more than one object for this singleton class");
		}
	}

	@SuppressWarnings("rawtypes")
	public static ParkingLotDataInformation getInstance() throws ParkingException {
		if (instance == null) {
			synchronized (ParkingLotDataInformation.class) {
				if (instance == null) {
					instance = new ParkingLotDataInformation();
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	public static ParkingLotDataInformation getInstance(int level, ParkingInformation info) throws ParkingException {
		if (instance == null) {
			synchronized (ParkingLotDataInformation.class) {
				if (instance == null) {
					instance = new ParkingLotDataInformation(level, info);
				}
			}
		}
		return instance;
	}
	
	public int parkCar(T vehicle) {
		int freeSlot;
		
		if(freeCarSlots.get() == 0) {
			return -1; 					
		} else {
			
			// Check if vehicle already parked 
			for(Vehicle v: carSlotMap.values()) {
				if(Vehicle.isSameVehicle(vehicle, v)) {
					return -2;
				}
			}
			
			freeSlot = strategy.getSlot();
			
			carSlotMap.put(freeSlot, vehicle);
			freeCarSlots.decrementAndGet();
			strategy.freeSlot(freeSlot);
		}
		
		return freeSlot;
	}
	
	public boolean leaveCar(int slot) {
		if(!carSlotMap.containsKey(slot)) {
			return false;
		}
		
		carSlotMap.remove(slot);
		freeCarSlots.incrementAndGet();
		strategy.add(slot);
		return true;
	}
	
	public List<String> getStatus(){
		List<String> status = new ArrayList<String>();
		
		for(int i = 1; i <= carSlots.get(); i++) {
			Vehicle v = carSlotMap.get(i);
			
			if(v != null) {
				status.add(i+ "\t\t" + v.getRegistrationNo() + "\t\t" + v.getColor());
			}
		}
		
		return status;
	}
	
	public int getFreeSlotsCount() {
		return freeCarSlots.get();
	}
	
	
	public List<String> getRegNumberForColor(String color)
	{
		List<String> statusList = new ArrayList<String>();
		for (int i = 1; i <= carSlots.get(); i++)
		{
			Vehicle vehicle = carSlotMap.get(i);
			if (vehicle != null && color.equalsIgnoreCase(vehicle.getColor()))
			{
				statusList.add(vehicle.getRegistrationNo());
			}
		}
		return statusList;
	}
	

	public List<Integer> getSlotNumbersFromColor(String colour)
	{
		List<Integer> slotList = new ArrayList<Integer>();
		for (int i = 1; i <= carSlots.get(); i++)
		{
			Vehicle vehicle = carSlotMap.get(i);
			if (vehicle != null && colour.equalsIgnoreCase(vehicle.getColor()))
			{
				slotList.add(i);
			}
		}
		return slotList;
	}
	
	
	public int getSlotNoFromRegistrationNo(String registrationNo)
	{
		int result = -1; // -1 represents not found
		for (int i = 1; i <= carSlots.get(); i++)
		{
			Vehicle vehicle = carSlotMap.get(i);
			if (vehicle!= null && registrationNo.equalsIgnoreCase(vehicle.getRegistrationNo()))
			{
				result = i;
			}
		}
		return result;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}
	
	
	public void doCleanUp()
	{
		this.level = new AtomicInteger(0);
		this.carSlots = new AtomicInteger();
		this.freeCarSlots = new AtomicInteger();
		this.strategy = null;
		
		carSlotMap.clear();
		carSlotMap = null;
		instance = null;
	}
}
