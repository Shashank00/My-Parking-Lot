package com.gojek.parkinglot.exceptions.parking;

public class ParkingException extends Exception {

private static final long serialVersionUID = -2902175126802621355L;
	
	/**
	 * @param message
	 * @param throwable
	 */
	public ParkingException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	
	/**
	 * @param message
	 */
	public ParkingException(String message)
	{
		super(message);
	}
	
	/**
	 * @param throwable
	 */
	public ParkingException(Throwable throwable)
	{
		super(throwable);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public ParkingException(String errorCode, String message, Throwable throwable)
	{
		super(message, throwable);
	}
	

}
