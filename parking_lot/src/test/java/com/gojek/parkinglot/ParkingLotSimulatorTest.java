package com.gojek.parkinglot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.gojek.parkinglot.exceptions.parking.ParkingException;
import com.gojek.parkinglot.model.car.Car;
import com.gojek.parkinglot.service.parking.ParkingService;
import com.gojek.parkinglot.service.parking.car.CarParkingService;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class ParkingLotSimulatorTest {
	private int level;

	@Test
	public void createParkingLot() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		service.createParkingLot(level, 100);
		assertTrue("Created parking lot with 100 slots".equalsIgnoreCase(outContent.toString().trim()));
		
		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void checkAlreadyExistingLot() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.createParkingLot(level, 100);
			service.createParkingLot(level, 20);
			fail("Exception should have been thrown");
		} catch (ParkingException e) {
			String message = "Parking lot already exists !!";
			assertEquals(message, e.getMessage());
		}

		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void testEmptyParkingLot() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.getStatus(level);
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 2);
		service.getStatus(level);

		assertEquals("Createdparkinglotwith2slots\nSlotNo.\tRegistrationNo.\tColour\nSorry,parkinglotisempty",
				outContent.toString().trim().replace(" ", ""));

		service = null;
		System.setOut(null);
	}

	@Test
	public void testParkingLotIsFull() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.park(level, new Car("AS-01-HH-1234", "White"));
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 1);
		service.park(level, new Car("AS-01-AA-1234", "White"));
		service.park(level, new Car("AS-01-BB-1001", "Blue"));

		assertEquals("Createdparkinglotwith1slots\nAllocatedslotnumber:1\nSorry,parkinglotisfull",
				outContent.toString().trim().replace(" ", ""));

		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void testLeave() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.remove(level, 1);
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 100);
		service.park(level, new Car("AS-01-HH-1234", "White"));
		service.park(level, new Car("AS-01-HH-9999", "White"));
		service.park(level, new Car("AS-01-BB-0001", "Black"));
		service.remove(level, 3);
		
		assertEquals(
		"Createdparkinglotwith100slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nAllocatedslotnumber:3\nSlotnumber3isfree",
		outContent.toString().trim().replace(" ", ""));

		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void testWhenVehicleAlreadyPresent() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.park(level, new Car("KA-01-HH-1234", "White"));
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 100);
		service.park(level, new Car("AS-01-HH-1234", "White"));
		service.park(level, new Car("AS-01-HH-1234", "White"));

		assertEquals("Createdparkinglotwith100slots\nAllocatedslotnumber:1\nSorry,vehicleisalreadyparked",
				outContent.toString().trim().replace(" ", ""));

		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void testStatus() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.getStatus(level);
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 2);
		service.park(level, new Car("AS-01-HH-1234", "White"));
		service.park(level, new Car("AS-01-HH-9999", "White"));
		service.getStatus(level);
		
		assertEquals(
		"Createdparkinglotwith2slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nSlotNo.\tRegistrationNo.\tColour\n1\t\tAS-01-HH-1234\t\tWhite\n2\t\tAS-01-HH-9999\t\tWhite",
		outContent.toString().trim().replace(" ", ""));

		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void testGetSlotNumberFromRegistrationNo() throws Exception {
		level = 1;
		ParkingService service = new CarParkingService();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.getSlotNumberFromRegistrationNo(level, "AS-01-HH-1234");
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 100);
		service.park(level, new Car("KA-01-MM-1234", "White"));
		service.park(level, new Car("KA-01-NN-9999", "White"));
		service.getSlotNumberFromRegistrationNo(level, "KA-01-MM-1234");

		assertEquals("Createdparkinglotwith100slots\n" + "Allocatedslotnumber:1\n" + "Allocatedslotnumber:2\n1",
				outContent.toString().trim().replace(" ", ""));
		service.getSlotNumberFromRegistrationNo(level, "KA-01-HH-1235");

		assertEquals(
				"Createdparkinglotwith100slots\n" + "Allocatedslotnumber:1\n" + "Allocatedslotnumber:2\n1\nNotFound",
				outContent.toString().trim().replace(" ", ""));

		service.doCleanup();
		System.setOut(null);
	}

	@Test
	public void testGetRegistrationNumbersForColor() throws Exception {

		level = 1;
		ParkingService service = new CarParkingService();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		try {
			service.getRegistrationNumbersForColor(level, "Green");
			fail("Exception should have been thrown");
		} catch (ParkingException e) {

			String message = "Parking lot does not exist !!";
			assertEquals(message, e.getMessage().trim());
		}

		service.createParkingLot(level, 100);
		service.park(level, new Car("AS-01-HH-1234", "White"));
		service.park(level, new Car("AS-01-HH-9999", "White"));
		service.getRegistrationNumbersForColor(level, "White");

		assertEquals(
				"Createdparkinglotwith100slots\n" + "Allocatedslotnumber:1\n"
						+ "Allocatedslotnumber:2\nAS-01-HH-1234,AS-01-HH-9999",
				outContent.toString().trim().replace(" ", ""));
		service.getRegistrationNumbersForColor(level, "Red");
		assertEquals("Createdparkinglotwith100slots\n" + "Allocatedslotnumber:1\n" + "Allocatedslotnumber:2\n"
				+ "AS-01-HH-1234,AS-01-HH-9999\nNotFound", outContent.toString().trim().replace(" ", ""));

		service.doCleanup();
		System.setOut(null);
	}
}
