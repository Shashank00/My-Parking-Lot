package com.gojek.parkinglot.model.car;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.gojek.parkinglot.constants.vehicle.VehicleType;
import com.gojek.parkinglot.model.Vehicle;

public class Car extends Vehicle {
	
	public Car(String registrationNo, String color) {
		super(registrationNo, color, VehicleType.CAR);
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);
	}
}
