package LibPurple.control;

public class PIDvalue
{
	
	public double kP;
	public double kI;
	public double kD;
	public double kF;
	
	public PIDvalue(double kP, double kI, double kD, double kF) 
	{
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
	}
	
	public PIDvalue(double kP, double kI, double kD) 
	{
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = 0;
	}

}
