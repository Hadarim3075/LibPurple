package LibPurple.sensors;


import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogPressureSensor {
	
	AnalogInput input;
	double VCC = 5; // The given voltage.
	
	public AnalogPressureSensor(int channel)
	{
		input = new AnalogInput(channel);
	}
	
	public double readPressure()
	{
		double Vout = input.getVoltage();
		double p = 275 * (Vout / VCC) - 27.5;
		return p;
	}

}
