package com.gojek.parkinglot.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.gojek.parkinglot.constants.vehicle.VehicleType;

public class Vehicle implements Externalizable {

	private String registrationNo 			= null;
	private String color 					= null;
	private VehicleType  type				= null;
	
	
	// No Argument Constructor as class implements Externalizable
	public Vehicle() {
		
	}
	
	public Vehicle(String registrationNo, String color, VehicleType type) {
		this.setRegistrationNo(registrationNo);
		this.setColor(color);
		this.setType(type);
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeObject(getRegistrationNo());
		out.writeObject(getColor());
		out.writeObject(getType());
		
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		
		String registrationNo = (String) in.readObject();
		String color = (String) in.readObject();
		VehicleType type = (VehicleType) in.readObject();
		
		// Call setters
		
		this.setRegistrationNo(registrationNo);
		this.setColor(color);
		this.setType(type);
		
	}

	public static boolean isSameVehicle(Vehicle v1, Vehicle v2) {
		if(v1.registrationNo.equalsIgnoreCase(v2.registrationNo))
			return true;
		return false;
	}
	
	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}
}
