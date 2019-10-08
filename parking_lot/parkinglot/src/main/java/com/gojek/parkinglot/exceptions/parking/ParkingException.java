package com.gojek.parkinglot.exceptions.parking;

public class ParkingException extends Exception {

private static final long serialVersionUID = -2902175126802621355L;
	
	private String		errorCode		= null;
	
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
	 * @param errorParameters
	 */
	public ParkingException(String errorCode, String message, Object[] errorParameters)
	{
		super(message);
		this.setErrorCode(errorCode);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public ParkingException(String errorCode, String message, Throwable throwable)
	{
		super(message, throwable);
		this.setErrorCode(errorCode);
	}
	
	public String getErrorCode()
	{
		return errorCode;
	}
	
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}
}
