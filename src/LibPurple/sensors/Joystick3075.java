package LibPurple.sensors;
import java.util.Arrays;

import LibPurple.utils.Polynom;
import LibPurple.utils.Utils;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Joystick3075 extends Joystick
{
	private double deadband = 0;
	private Polynom polynom;
	private int[] axisInverts = new int[7];


	public Joystick3075(int port)
	{
		super(port);
		Arrays.fill(axisInverts, 1);
	}

	public void setPolynom(Polynom p)
	{
		this.polynom = p;
	}

	public void invertAxis(int axis, boolean inverted)
	{
		axisInverts[axis] = inverted ? -1 : 1;
	}

	public void invertX(boolean inverted)
	{
		axisInverts[AxisType.kX.value] = inverted ? -1 : 1;
	}

	public void invertY(boolean inverted)
	{
		axisInverts[AxisType.kY.value] = inverted ? -1 : 1;
	}
	
	public boolean isInverted(int axis)
	{
		return axisInverts[axis] == -1;
	}

	@Override
	public double getRawAxis(final int axis)
	{
		return super.getRawAxis(axis) * axisInverts[axis];
	}

	public double xGet()
	{
		double x = getRawAxis(AxisType.kX.value);
		x = Utils.deadband(x, deadband);
		return polynom.getValue(x);
	}

	public double yGet()
	{
		double y = getRawAxis(AxisType.kY.value);
		y = Utils.deadband(y, deadband);
		return polynom.getValue(y);
	}

	public double getDeadband() {
		return deadband;
	}

	public void setDeadband(double deadband) {
		this.deadband = deadband;
	}
	
	public Command reverseDirection(int axis)
	{
		return new RobotReverseDirection(this, axis);
	}
}

class RobotReverseDirection extends Command
{
	
	Joystick3075 joystick;
	int axis;
	
	public RobotReverseDirection(Joystick3075 joystick, int axis)
	{
		this.joystick = joystick;
		this.axis = axis;
	}
	
	@Override
	protected void initialize() {
		joystick.invertAxis(axis, !joystick.isInverted(axis));
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
}
