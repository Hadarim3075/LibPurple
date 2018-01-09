package LibPurple.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;

public class Utils
{
	/**
	* Takes two numbers and check if they are closer than dif
	**/
	public static boolean inRange(double value, double target, double deviation)
	{
		return Math.abs(value-target) <= deviation;
	}

	public static double deadband(double value, double deadband)
	{
		return Math.abs(value) < deadband ? 0 : Math.signum(value) * (Math.abs(value) - deadband) / (1 - deadband);
	}

	public static double accellimit(double currentValue, double lastValue, double accellimit)
	{
		return  lastValue + (Math.signum(currentValue - lastValue) * Math.min(accellimit, Math.abs(currentValue - lastValue)));
	}

	public static double motorBound(double value, double motorBound)
    {
    	return Math.signum(value) * (Math.abs(value) - (motorBound * Math.abs(value)) + motorBound);
	}

	public static double[] arcadeDrive(double y, double x)
	{
		double[] arr = new double[2];
		double max = Math.max(Math.abs(y), Math.abs(x));
		double sum = y + x;
		double dif = y - x;
		if(y >= 0)
		{
			if(x >= 0)
			{
				arr[0] = max;
				arr[1] = dif;
			}
			else
			{
				arr[0] = sum;
				arr[1] = max;
			}
		}
		else
		{
			if(x >= 0)
			{
				arr[0] = sum;
				arr[1] = -max;
			}
			else
			{
				arr[0] = -max;
				arr[1] = dif;
			}
		}
		max = 0;
		sum = 0;
		dif = 0;
		return arr;
	}

	public static double[] arcadeDrive(double y, double x, double turnSensitivity)
	{
		return arcadeDrive(y, x * turnSensitivity);
	}

	public static double powerValue(double value, double power)
	{
		return Math.signum(value)*Math.abs(Math.pow(Math.abs(value), power));
	}

	/**
	 * Prints an error to DriverStation with the cause and location
	 * @param err The error message
	 */
	public static void printErr(String err)
	{
		DriverStation.reportError(err, true);
	}

	/**
	 * Prints a message to DriverStation
	 * @param msg The message you want to print
	 */
	public static void print(String msg)
	{
		DriverStation.reportError(msg, false);;
	}

	public static void batteryWatcher()
	{
		if(DriverStation.getInstance().getBatteryVoltage() < 10)
			print("Battery is low! (Battery below 10V)");
	}

	public static void clearSticky()
	{
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		pdp.clearStickyFaults();
	}

	public static double[] driveCurve(double radius, double angle, double robotWidth)
	{
		double[] arr = {(radius-robotWidth/2)*angle, (radius+robotWidth/2)*angle};
		return arr;
	}

	/**
	 * This is a requires like the Command's one but for any object.
	 * The given Command will be canceled if the given object is null.
	 * Use inside a command example: requires(this, Components.systemStick);
	 * @param command the command that should be canceld of the object is null.
	 * @param object the object that is required.
	 * Contact Shahar for help.
	 */
	public static void requires(Command command, Object object)
	{
		if(object == null)
			command.cancel();
	}

	/**
	 * Calculates the y value of a point on a linear interpolation function.
	 * @param points A sorted Point3075 array that will be used for the intepolated function
	 * @param x The value for calculating its y.
	 * @return y value for the specified x.
	 */
	public static double linearInterpolation(Point3075[] points, double x)
	{
		// Making sure that x is within the points array range.
		// if not, return its y value on the closest line.
		if (x < points[0].getX())
		{
			return Point3075.getLine(points[0], points[1], x);
		}
		else if (x > points[points.length - 1].getX())
		{
			return Point3075.getLine(points[points.length - 1], points[points.length - 2], x);
		}

		for(int i = 0; i < points.length - 1; i++)
		{
			if(points[i].getX() < x && points[i + 1].getX() > x)
			{
				return Point3075.getLine(points[i], points[i + 1], x);
			}
		}

		// Prevent return errors, this should never happen
		return 0;
	}

	// In the memory of our beloved Kellner

//	public static double[] driveCurveByDistance(double radius, double distance, double robotWidth)
//	{
//		double[] arr = {(radius-robotWidth/2)*distance/radius, (radius+robotWidth/2)*distance/radius};
//		return arr;
//	}

//	public static double[] driveCurveByAngle(double angle, double distance, double robotWidth)
//	{
//		double[] arr = {distance - (robotWidth/2)*angle, distance + (robotWidth/2)*angle};
//		return arr;
//	}
}