package LibPurple.sensors;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderTalon3075 implements Encoder3075{
	
	private WPI_TalonSRX myTalon;
	private double dpp = 1;
	private double resetPoint = 0;
	private double inverted = 1;
	private static double encoderPulsesPerRotation = 4;
	PIDSourceType type = PIDSourceType.kDisplacement;
	
	private static double minimumValue = 0.0005;
	
	
	public EncoderTalon3075(WPI_TalonSRX talon)
	{
		myTalon = talon;
	}
	
	public EncoderTalon3075(WPI_TalonSRX talon, boolean inverted)
	{
		myTalon = talon;
		this.inverted = inverted ? -1 : 1;
	}
	
	public void setPulsesPerRotation(double pulses)
	{
		encoderPulsesPerRotation = pulses;
	}
	
	public void setReverseDirection(boolean reverseDirection)
	{
		this.inverted = reverseDirection ? -1 : 1;
	}
	
	/**
	 * 
	 * @return Encoder's rate for 100ms.
	 */
	
	public double getRate()
	{
		double returnValue = (myTalon.getSelectedSensorVelocity(0) / dpp) * inverted * 10;
		return Math.abs(returnValue) < minimumValue ? 0 : returnValue;
	}
	
	public double getRawSpeed()
	{
		return myTalon.getSelectedSensorVelocity(0) * inverted;
	}
	
	public double getRawPosition()
	{
		return myTalon.getSelectedSensorPosition(0) * inverted;
	}
	
	public double getDistance()
	{
		double returnValue = (myTalon.getSelectedSensorPosition(0) - resetPoint) / dpp * inverted;
		return Math.abs(returnValue) < minimumValue ? 0 : returnValue;
	}
	
	public void setDistancePerPulse(double dpp)
	{
		this.dpp = dpp;
	}
	
	public void reset()
	{
		resetPoint = myTalon.getSelectedSensorPosition(0);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public double pidGet()
	{
		switch(type)
		{
		case kDisplacement: 
			return getDistance();
		case kRate:
			return getRate();
		default:
			break;
		}
		
		return getDistance();
	}

	@Override
	public void setPIDSourceType(PIDSourceType type) 
	{
		this.type = type;
	}
}
