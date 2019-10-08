package com.gojek.parkinglot.processor;

import com.gojek.parkinglot.constants.command.Command;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.car.Car;
import com.gojek.parkinglot.service.parking.ParkingService;

public class RequestProcessor {
	
	private ParkingService parkingService;
	
	public void initializeService(ParkingService parkingService){
		this.parkingService = parkingService;
	}
	
	public boolean validateRequest(String request) {
		boolean isValid = true;
		
		try {
			String[] requestInformation = request.split(" ");

			if (requestInformation != null && requestInformation.length > 0) {
				if (requestInformation[0].equals(Command.STATUS.getCommandString())) {
					isValid = requestInformation.length == 1;
				} else if (requestInformation[0].equals(Command.CREATE.getCommandString())
						|| requestInformation[0].equals(Command.LEAVE.getCommandString())
						|| requestInformation[0].equals(Command.FETCH_SLOTS_BY_COLOR.getCommandString())
						|| requestInformation[0].equals(Command.FETCH_REGISTRATION_NUMBERS_BY_COLOR.getCommandString())
						|| requestInformation[0]
								.equals(Command.FETCH_SLOT_FROM_REGISTRATION_NUMBER.getCommandString())) {
					isValid = requestInformation.length == 2;
				} else if (requestInformation[0].equals(Command.PARK.getCommandString())) {
					isValid = requestInformation.length == 3;
				} else {
					isValid = false;
				}
			} else {
				isValid = false;
			}

		} catch(Exception e) {
			isValid = false;
		}
		
		return isValid;
	}
	
	public void executeRequest(String request) throws ParkingException {
		
		int defaultLevel = 1;
		
		String[] information = request.split(" ");
		
		String key = information[0];
		
		Command command = Command.getCommand(key); 
		
		switch(command) {
			case CREATE:
				try {
					int capacity = Integer.parseInt(information[1]);
					parkingService.createParkingLot(defaultLevel, capacity);
					
				} catch (Exception e) {
					throw new ParkingException("Error in creating Parking Lot");
				}
				break;
			case PARK:
				parkingService.park(defaultLevel, new Car(information[1], information[2]));
				break;
			case LEAVE:
				try {
					int slot = Integer.parseInt(information[1]);
					parkingService.remove(defaultLevel, slot);
				} catch (Exception e) {
					throw new ParkingException("Invalid value");
				}
				break;
			case STATUS:
				parkingService.getStatus(defaultLevel);
				break;
			case FETCH_REGISTRATION_NUMBERS_BY_COLOR:
				parkingService.getRegistrationNumbersForColor(defaultLevel, information[1]);
				break;
			case FETCH_SLOTS_BY_COLOR:
				parkingService.getSlotNumbersFromColor(defaultLevel, information[1]);
				break;
			case FETCH_SLOT_FROM_REGISTRATION_NUMBER:
				parkingService.getSlotNumberFromRegistrationNo(defaultLevel, information[1]);
				break;
			default:
				break;
		}
	}
}
