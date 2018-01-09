package LibPurple.utils;


import java.util.ArrayList;
import java.util.Arrays;


public class Rectangle {
	
	private Point3075 topRight, topLeft, bottomRight, bottomLeft;
	
	private double targetRatio = 1;

	public Rectangle(Point3075 p[])
	{
		assert p.length < 4;
		
		topLeft = Point3075.leftestPoint(p);
		ArrayList<Point3075> temPoint3075s = new ArrayList<Point3075>(Arrays.asList(p));
		temPoint3075s.remove(topLeft);
		
		bottomLeft = Point3075.leftestPoint(Arrays.copyOf(temPoint3075s.toArray(), temPoint3075s.toArray().length, Point3075[].class));
		
		temPoint3075s.remove(bottomLeft);
		topRight = temPoint3075s.get(0);
		bottomRight = temPoint3075s.get(1);
		
		sortTopAndBottom(topLeft, bottomLeft);
		sortTopAndBottom(topRight, bottomRight);
		
	}
	
	// doesn't matter if really top and bottom, name of the original variable's
	// name is important.
	public void sortTopAndBottom(Point3075 top, Point3075 bottom)
	{
		if(top.getY() > bottom.getY())
		{
			Point3075 temp = top;
			top = bottom;
			bottom = temp;
		}
	}
	
	/**
	 * calculates the quadrate's center by average of Point3075s.
	 * @return the Point3075 of the quadrate's center
	 */
	public Point3075 getCenter()
	{
		double avgX = (topLeft.getX() + topRight.getX() + bottomLeft.getX() + bottomRight.getX()) / 4;
		double avgY = (topLeft.getY() + topRight.getY() + bottomLeft.getY() + bottomRight.getY()) / 4;
		return new Point3075(avgX, avgY);
	}
	
	/**
	 * calculates the average length of the side edges (height).
	 * @return the average length of the side edges.
	 */
	public double getHeight()
	{
		return (Point3075.distance(topLeft, bottomLeft) + Point3075.distance(topRight, bottomRight))/2;
	}
	
	public double getWidth()
	{
		return (Point3075.distance(topLeft, topRight) + Point3075.distance(bottomLeft, bottomRight))/2;
	}
	
	
	//TODO fix this some day :/
	public double getAngle(double FOV, double imageWidth)
	{
		double x = getCenter().getX();
		double absAngle = x / (Math.toRadians(FOV) * imageWidth);
    	double realAngle = absAngle - Math.toRadians(FOV)/2;
    	realAngle = Math.toDegrees(realAngle);
    	return realAngle;

	}
	
	public String toString()
	{
		return topLeft + "_____" + topRight + "\n|\n|\n|\n" + bottomLeft + "_____" + bottomRight + "\n";
	}
	
	public double getTargetRatio() {
		return targetRatio;
	}

	public void setTargetRatio(double targetRatio) {
		this.targetRatio = targetRatio;
	}
}
