package com.gojek.parkinglot.model.strategy;

public interface ParkingStrategy {
	
	public void add(int i);
	
	public int getSlot();
	
	public void freeSlot(int slot);
}
