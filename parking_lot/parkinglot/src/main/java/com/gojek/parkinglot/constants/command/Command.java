package com.gojek.parkinglot.constants.command;

public enum Command {
	
	CREATE("create_parking_lot"),
	PARK("park"),
	LEAVE("leave"),
	STATUS("status"),
	FETCH_REGISTRATION_NUMBERS_BY_COLOR("registration_numbers_for_cars_with_color"),
	FETCH_SLOTS_BY_COLOR("slot_numbers_for_cars_with_color"),
	FETCH_SLOT_FROM_REGISTRATION_NUMBER("slot_number_for_registration_number");
	
	private String command;
	
	private Command(String command) {
		this.command = command;
	}
	
	public String getCommandString() {
		return command;
	}
	
	public static Command getCommand(String command) {
		if(command.equals(CREATE.command)) {
			return CREATE;
		} else if (command.equals(PARK.command)) {
			return PARK;
		} else if (command.equals(LEAVE.command)) {
			return LEAVE;
		} else if (command.equals(STATUS.command)) {
			return STATUS;
		} else if (command.equals(FETCH_REGISTRATION_NUMBERS_BY_COLOR.command)) {
			return FETCH_REGISTRATION_NUMBERS_BY_COLOR;
		} else if (command.equals(FETCH_SLOTS_BY_COLOR.command)) {
			return FETCH_SLOTS_BY_COLOR;
		} else if (command.equals(FETCH_SLOT_FROM_REGISTRATION_NUMBER.command)) {
			return FETCH_SLOT_FROM_REGISTRATION_NUMBER;
		} else
			return null;
	}
}
