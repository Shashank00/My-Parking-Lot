package com.gojek.parkinglot.model.strategy.implementation;

import java.util.TreeSet;
import com.gojek.parkinglot.model.strategy.ParkingStrategy;

public class NearestEntryParkingStrategy implements ParkingStrategy {

	private TreeSet<Integer> freeSlots;
	
	public NearestEntryParkingStrategy() {
		freeSlots = new TreeSet<Integer>();
	}
	
	public void add(int i) {
		freeSlots.add(i);		
	}

	public int getSlot() {
		if(freeSlots != null) {
			return freeSlots.first();		
		}
		else
			return -1;
	}

	public void freeSlot(int slot) {
		if(freeSlots != null) {
			freeSlots.remove(slot);		
		}
	}

}
