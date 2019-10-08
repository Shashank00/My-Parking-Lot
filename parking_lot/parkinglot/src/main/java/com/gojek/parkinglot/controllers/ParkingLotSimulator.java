package com.gojek.parkinglot.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.gojek.parkinglot.constants.vehicle.VehicleType;
import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.processor.RequestProcessor;
import com.gojek.parkinglot.service.parking.factory.ParkingServiceFactory;

public class ParkingLotSimulator {

	public static void main(String[] args) {
		
		RequestProcessor process = new RequestProcessor();
		
		// For now we consider only Cars
		process.initializeService(ParkingServiceFactory.getParkingServiceInstance(VehicleType.CAR));
		
		BufferedReader reader = null;
		
		try {
			
			System.out.println("--------------------------------------------------------");
			System.out.println("                   GOJEK PARKING LOT                    ");
			System.out.println("--------------------------------------------------------");
			
			printUseDetails();
			
			switch(args.length) {
			
				case 0: {
					System.out.println("-- Enter 'exit' to quit execution --");
					System.out.print("Enter input: ");
					while(true) {
						try {
							reader = new BufferedReader(new InputStreamReader(System.in));
							String command = reader.readLine().trim();
							
							if(!command.equalsIgnoreCase("exit")) {
								if(process.validateRequest(command)) {
									try {
										process.executeRequest(command);
									} catch (Exception e) {
										System.out.println(e.getMessage());
									}
								} else {
									// Show correct commands to be entered
									printUseDetails();
								}
							} else {
								break;
							}
						} catch(Exception e) {
							throw new ParkingException("Invalid Request !!");
						}
					}
				}
				break;
				
				case 1: {
					File inputFile = new File(args[0]);
					try {
						reader = new BufferedReader(new FileReader(inputFile));
						int line = 1; // Keeps tract of lines in input file
						
						String command = null;
						
						while((command = reader.readLine()) != null) {
							 command = command.trim(); 
						if (process.validateRequest(command)) {
							try {
								process.executeRequest(command);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						} else {
							System.out.println("Invalid command at line: " + line + " command: " + command);
						} line++;
						}
					} catch (Exception e) {
						throw new ParkingException("File not found !!");
					}
					
					break;
				}
				
				default:
					System.out.println("Invalid input !! Should enter java -jar <jar_file_path> <input_file_path>");
			}
			
		} catch(ParkingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				
			}
		}
	}
	
	private static void printUseDetails() {
		StringBuffer buffer = new StringBuffer();
		buffer = buffer.append("Please Enter one of the below commands. {variable} to be replaced !").append("\n");
		buffer = buffer.append("1) Create parking lot of capacity c : create_parking_lot {capacity}").append("\n");
		buffer = buffer.append("2) Park a car : park <<car_number>> {car_color}").append("\n");
		buffer = buffer.append("3) Remove car from parking spot : leave {slot_number}").append("\n");
		buffer = buffer.append("4) Get parking slot status s : status").append("\n");

		buffer = buffer.append(
				"5) Get registration no for the provided car color : registration_numbers_for_cars_with_color {car_color}")
				.append("\n");
		buffer = buffer
				.append("6) Get slot numbers for the provided car color : slot_numbers_for_cars_with_color {car_color}")
				.append("\n");
		buffer = buffer.append(
				"7) Get slot number for the given car number : slot_number_for_registration_number {car_number}")
				.append("\n");
		System.out.println(buffer.toString());
	}

}