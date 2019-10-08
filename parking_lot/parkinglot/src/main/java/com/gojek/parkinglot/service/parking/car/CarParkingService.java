package com.gojek.parkinglot.service.parking.car;

import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.gojek.parkinglot.constants.vehicle.VehicleType;
import com.gojek.parkinglot.dataaccess.ParkingLotManager;
import com.gojek.parkinglot.dataaccess.factory.ParkingLotManagerFactory;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.strategy.ParkingStrategy;
import com.gojek.parkinglot.model.strategy.implementation.NearestEntryParkingStrategy;
import com.gojek.parkinglot.service.parking.ParkingService;

public class CarParkingService implements ParkingService {

	private ParkingLotManager<Vehicle> manager  = null;
	
	private ReentrantReadWriteLock     lock 	= new ReentrantReadWriteLock();
	
	public static class ParkingInformation {
		
		public Integer capacity;
		public ParkingStrategy strategy;
		
		public ParkingInformation(Integer capacity, ParkingStrategy strategy) {
			this.capacity = capacity;
			this.strategy = strategy;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void createParkingLot(int level, int capacity) throws ParkingException {
		if (manager != null) {
			throw new ParkingException("Parking Manager is already active !!");
		}

		HashMap<Integer, ParkingInformation> floorInfo = new HashMap<Integer, ParkingInformation>();

		floorInfo.put(level, new ParkingInformation(capacity, new NearestEntryParkingStrategy()));
		
		this.manager = (ParkingLotManager<Vehicle>) ParkingLotManagerFactory.getManagerInstance(floorInfo, VehicleType.CAR);
		
		System.out.println("Created parking lot with car parking slots" + capacity);
	}

	public void park(int level, Vehicle vehicle) throws ParkingException {
		if(manager == null) {
			throw new ParkingException("No manager to manage Parking Lot !!");
		}
		
		int result = 0;
		lock.writeLock().lock();
		try {
			result = manager.parkCar(level, vehicle);
			if(result == -1) {
				System.out.println("Sorry, parking lot is full");
			} else if(result == -2) {
				System.out.println("Vehicle already parked!!");
			} else {
				System.out.println("Allocated Slot number: " + result);
			}
		} catch (Exception e) {
			
			throw new ParkingException("Processing error!!");
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void remove(int level, int slotNo) throws ParkingException {
		
		if(manager == null) {
			throw new ParkingException("No manager to manage Parking Lot!!");
		}
		
		lock.writeLock().lock();
		try {
			if(manager.leaveCar(level, slotNo)) {
				System.out.println("Slot number " + slotNo + "is free");
			} else {
				System.out.println("Slot number " + slotNo + "is occupied");
			}
		} catch (Exception e) {
			throw new ParkingException("Error in removing vehicle!!");
		} finally {
			lock.writeLock().unlock();
		}
		
	}

	public void getStatus(int level) throws ParkingException {
		
		if(manager == null) {
			throw new ParkingException("No manager to manage Parking Lot!!");
		}
		lock.readLock().lock();
		try {
			System.out.println("Slot No.\tRegistration No.\tColor");
			List<String> status = manager.getStatus(level);
			
			if(status.size() == 0) {
				System.out.println("Sorry, parking lot is empty");
			} else {
				for(String s: status) {
					System.out.println(s);
				}
			}
		} catch (Exception e) {
			throw new ParkingException("Processing error!!");
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void getRegistrationNumbersForColor(int level, String color) throws ParkingException {
		
		if(manager == null) {
			throw new ParkingException("No manager to manage Parking Lot!!");
		}
		lock.readLock().lock();
		try
		{
			List<String> registrationList = manager.getRegistrationNumbersFromColor(level, color);
			if (registrationList.size() == 0)
				System.out.println("Not Found");
			else
				System.out.println(String.join(",", registrationList));
		}
		catch (Exception e)
		{
			throw new ParkingException("Processing error");
		}
		finally
		{
			lock.readLock().unlock();
		}
		
	}

	public void getSlotNumbersFromColor(int level, String color) throws ParkingException {
		
		if(manager == null) {
			throw new ParkingException("No manager to manage Parking Lot!!");
		}
		lock.readLock().lock();
		try
		{
			List<Integer> slotList = manager.getSlotNumbersFromColor(level, color);
			if (slotList.size() == 0)
				System.out.println("Not Found");
			StringJoiner joiner = new StringJoiner(",");
			for (Integer slot : slotList)
			{
				joiner.add(slot + "");
			}
			System.out.println(joiner.toString());
		}
		catch (Exception e)
		{
			throw new ParkingException("Processing error");
		}
		finally
		{
			lock.readLock().unlock();
		}
		
	}

	public int getSlotNumberFromRegistrationNo(int level, String registrationNo) throws ParkingException {
		
		int value = -1;
		lock.readLock().lock();
		try
		{
			value = manager.getSlotNumberFromRegistrationNumber(level, registrationNo);
			System.out.println(value != -1 ? value : "Not Found");
		}
		catch (Exception e)
		{
			throw new ParkingException("Processing error");
		}
		finally
		{
			lock.readLock().unlock();
		}
		return value;
	}
	
	public void doCleanup()
	{
		if (manager != null)
			manager.clean();
	}

}
