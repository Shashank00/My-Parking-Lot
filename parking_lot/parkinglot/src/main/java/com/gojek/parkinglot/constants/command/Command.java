package com.gojek.parkinglot.constants.command;

public enum Command {
	
	CREATE("create_parking_lot"),
	PARK("park"),
	LEAVE("leave"),
	STATUS("status"),
	FETCH_CAR_BY_COLOR(""),
	FETCH_SLOT_BY_COLOR(""),
	FETCH_SLOT_FROM_REGISTRATION_NUMBER("");
	
	private final String command;
	
	private Command(String command) {
		this.command = command;
	}
}
