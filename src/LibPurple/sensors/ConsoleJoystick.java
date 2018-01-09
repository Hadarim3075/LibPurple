package LibPurple.sensors;

public class ConsoleJoystick extends Joystick3075{
// References:
// http://wpilib.screenstepslive.com/s/4485/m/13503/l/599677-frc-driver-station-powered-by-ni-labview \ http://i.imgur.com/bGfrf6m.png
// http://www.team358.org/files/programming/ControlSystem2015-2019/images/XBoxControlMapping.jpg
	public ConsoleJoystick(int port)
	{
		super(port);
	}
	public double getR2()
	{
		return this.getRawAxis(3);
	}
	public double getL2()
	{
		return this.getRawAxis(2);
	}
	// Driving like in video games such as GTA, Rocket League
	public double yGet()
	{
		return -this.getRawAxis(1);
	}
	public double xGet()
	{
		return this.getRawAxis(3) - this.getRawAxis(2);
	}
}